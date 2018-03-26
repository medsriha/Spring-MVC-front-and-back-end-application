package com.mf.cfs.controller;

import com.mf.cfs.dao.EmployeeDao;
import com.mf.cfs.domain.*;
import com.mf.cfs.form.BuyFundForm;
import com.mf.cfs.form.CreateFundForm;
import com.mf.cfs.form.SellFundForm;
import com.mf.cfs.form.SetTransitionDateForm;
import com.mf.cfs.service.*;
import com.mf.cfs.util.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

//@RequestMapping("/")
@Controller
public class FundController {

    @Autowired
    private FundService fundService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmployeeDao employeeDao;


    @RequestMapping(value = "/customer/research_fund", method = RequestMethod.GET)
    public String loadFundResearchForm(Principal principal, Model model) {
        List<FundResearch> researches = fundService.getFundResearch();
        int id = Integer.parseInt(principal.getName());
        model.addAttribute("customer", accountService.findCustomer(id));
        model.addAttribute("researches", researches);
        model.addAttribute("form", new BuyFundForm(fundService.getAllFunds()));
        return "mutual_funds";
    }



    @RequestMapping(value = "/update_fund_prices", method = RequestMethod.GET)
    public String showUpdateFundForm(Model model) {
        List<Fund> fList = new ArrayList<Fund>();
        fList = fundService.getAllFunds();
        model.addAttribute("funds", fList);
        return "update_fund_form";
    }

    @PostMapping("/employee/create_fund")
    public String createFund(Principal principal,
                           Model model,
                           @ModelAttribute(value = "createFund") @Valid CreateFundForm form,
                           BindingResult result)    {

        if (result.hasErrors()) {
            Employee employee = employeeDao.findOne(Integer.parseInt(principal.getName()));
            EmployeeDetail employeeDetail = new EmployeeDetail();
            employeeDetail.setEmployee(employee);
            employeeDetail.setCustomerList(accountService.getCustomerList());
            model.addAttribute("employeeDetail", employeeDetail);
            model.addAttribute("employee", employee);
            return "employee_dashboard";
        }

        List<Customer> customerList = accountService.getCustomerList();
        boolean flag = fundService.createFund(form, result);
        model.addAttribute("flag", flag);
        if(flag == true)    {
            model.addAttribute("reminder", Reminder.CREATE_FUND_SUCCESS.getReminder());
            return "reminder";
        }
        return "employee_dashboard";
    }
}
