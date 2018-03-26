package com.mf.cfs.controller;

import com.mf.cfs.dao.CustomerDao;
import com.mf.cfs.dao.EmployeeDao;
import com.mf.cfs.dao.LastDateDao;
import com.mf.cfs.dao.PositionDao;
import com.mf.cfs.domain.*;
import com.mf.cfs.form.*;
import com.mf.cfs.form.RequestCheckForm;
import com.mf.cfs.service.AccountService;
import com.mf.cfs.service.TransactionService;
import com.mf.cfs.service.TransitionDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ViewAccountController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransitionDayService transitionDayService;


    private final SimpleGrantedAuthority customer = new SimpleGrantedAuthority("Customer");
    private final SimpleGrantedAuthority employee = new SimpleGrantedAuthority("Employee");


    @RequestMapping(value = {"/dashboard", "/", "/employee", "/customer"})
    public String displayDashboard(Principal principal) {
        AbstractAuthenticationToken currentUser = (AbstractAuthenticationToken)principal;

        // If the user is a customer
        if (currentUser.getAuthorities().contains(customer)) {
            System.out.println("Customer");
            return "redirect:/customer/dashboard";
        }
        // If the user is an employee
        else if (currentUser.getAuthorities().contains(employee)) {
            System.out.println("Employee");
            return "redirect:/employee/dashboard";
        }
        System.out.println("Else");

        return "login";
    }

    @GetMapping("/customer/dashboard")
    public String customerDashboard(Principal principal, Model model) {
        int id = Integer.parseInt(principal.getName());

        List<TransactionForDisplay> transactions = transactionService.getTransactions(id);
        List<PositionForDisplay> positions = accountService.getPositionForDisplays(id);

        model.addAttribute("date", transitionDayService.getDate());
        model.addAttribute("transactions", transactions);
        model.addAttribute("positions", positions);
        model.addAttribute("form", new RequestCheckForm());
        model.addAttribute("sellForm", new SellFundForm());
        model.addAttribute("customer", accountService.findCustomer(id));

        return "dashboard";
    }

    @GetMapping("/employee/dashboard")
    public String employeeDashboard(Principal principal, Model model) {
        Employee employee = employeeDao.findOne(Integer.parseInt(principal.getName()));
        EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setEmployee(employee);
        employeeDetail.setSetTransitionDateForm(new SetTransitionDateForm());
        employeeDetail.setCustomerList(accountService.getCustomerList());
        model.addAttribute("createFund", new CreateFundForm());
        model.addAttribute("employeeDetail", employeeDetail);
        model.addAttribute("employee", employee);
        return "employee_dashboard";
    }

    @GetMapping("/employee/customer_account/{id}")
    public String displayCustomer(Model model,
                                  @PathVariable Integer id) {
        Customer customer = customerDao.findOne(id);
        if (customer == null) {
            return "redirect:/error";
        }
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setTransactionForDisplays(transactionService.getTransactions(customer.getId()));
        customerDetail.setCustomer(customer);
        customerDetail.setPositions(accountService.getPositionForDisplays(customer.getId()));
        DepositCheckForm form = new DepositCheckForm();
        form.setUsername(customer.getUsername());
        model.addAttribute("date", transitionDayService.getDate());
        model.addAttribute("form", form);
        model.addAttribute("customerDetail", customerDetail);
        model.addAttribute("specialPassWd", new SpecialPasswordForm());
        return "customer_view_account";
    }
}
