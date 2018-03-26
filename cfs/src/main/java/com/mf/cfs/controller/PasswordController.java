package com.mf.cfs.controller;

import com.mf.cfs.dao.CustomerDao;
import com.mf.cfs.domain.Customer;
import com.mf.cfs.domain.CustomerDetail;
import com.mf.cfs.form.DepositCheckForm;
import com.mf.cfs.form.RegularPasswordForm;
import com.mf.cfs.form.SpecialPasswordForm;
import com.mf.cfs.service.AccountService;
import com.mf.cfs.service.TransactionService;
import com.mf.cfs.util.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class PasswordController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/employee/changePassWd", method = RequestMethod.GET)
    public String showChangeEmployeePassWdForm(Model model){
        model.addAttribute("regularPassWd", new RegularPasswordForm());
        return "change_password_employee";
    }

    @RequestMapping(value = "/customer/changePassWd", method = RequestMethod.GET)
    public String showChangeCustomerPassWdForm(Principal principal, Model model){
        int id = Integer.valueOf(principal.getName());
        model.addAttribute("regularPassWd", new RegularPasswordForm());
        model.addAttribute("customer", accountService.findCustomer(id));
        return "change_password";
    }

    @RequestMapping(value = "/employee/changePassWd.do", method = RequestMethod.POST)
    public String changeEmployeePassWd(Principal principal,
                                           Model model,
                                           @ModelAttribute(value = "form") @Valid RegularPasswordForm form,
                                           BindingResult result){
        boolean isSuccess = false;
        if (!result.hasErrors()){
            try {
                isSuccess = accountService.changePassWdByEmployee(form, Integer.parseInt(principal.getName()));
            } catch(RuntimeException e){
                form.setErrMsg(e.getMessage());
                model.addAttribute("regularPassWd",form);
                return "change_password_employee";
            }
        }
        if (isSuccess) {
            model.addAttribute("reminder", Reminder.CHANGE_PASSWORD_SUCCESS.getReminder());
            return "reminder";
        }
        form.setErrMsg(errorDisplay(result));
        model.addAttribute("regularPassWd", form);
        return "change_password_employee";
    }

    @RequestMapping(value = "/employee/changeCustomerPassWd.do", method = RequestMethod.POST)
    public String changeCustomerPassWdByEmployee(Model model,
                                                     @ModelAttribute(value = "form") @Valid SpecialPasswordForm form,
                                                     BindingResult result){
        System.out.println("Print Customer ID:"+form.getUserID());
        boolean isSuccess = false;
        if (!result.hasErrors()){
            try {
                isSuccess = accountService.changeCustomerPassWdByEmployee(form);
            } catch (RuntimeException e){
                model.addAttribute("reminder", Reminder.INVALID_CUSTOMER_ID.getReminder());
                return "reminder";
            }
        }
        if (isSuccess){
            model.addAttribute("reminder", Reminder.RESET_CUSTOMER_PASSWORD_SUCCESS.getReminder());
            return "reminder";
        }
        form.setErrMsg(errorDisplay(result));

        return displayRefresh(model, form);
    }

    private String displayRefresh(Model model, SpecialPasswordForm passwordForm){
        Customer customer = customerDao.findOne(passwordForm.getUserID());
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setTransactionForDisplays(transactionService.getTransactions(customer.getId()));
        customerDetail.setCustomer(customer);
        customerDetail.setPositions(accountService.getPositionForDisplays(customer.getId()));
        DepositCheckForm form = new DepositCheckForm();
        form.setUsername(customer.getUsername());
        model.addAttribute("form", form);
        model.addAttribute("customerDetail", customerDetail);
        model.addAttribute("specialPassWd", passwordForm);
        return "customer_view_account";
    }


    @RequestMapping(value = "/customer/changePassWd.do", method = RequestMethod.POST)
    public String changeCustomerPassWd(Principal principal,
                                           Model model,
                                           @ModelAttribute(value = "form") @Valid RegularPasswordForm form,
                                           BindingResult result){
        System.out.println(Integer.parseInt(principal.getName()));
        int id = Integer.valueOf(principal.getName());
        boolean isSuccess = false;
        if (!result.hasErrors()){
            try {
                isSuccess = accountService.changePassWdByCustomer(form, Integer.parseInt(principal.getName()));
            } catch (RuntimeException e){
                form.setErrMsg(e.getMessage());
                model.addAttribute("regularPassWd", form);
                model.addAttribute("customer", accountService.findCustomer(id));
                return "change_password";
            }
        }
        if (isSuccess){
            model.addAttribute("reminder", Reminder.CHANGE_PASSWORD_SUCCESS.getReminder());
            return "reminder";
        }
        form.setErrMsg(errorDisplay(result));
        model.addAttribute("regularPassWd", form);
        model.addAttribute("customer", accountService.findCustomer(id));
        return "change_password";
    }

    private List<String> errorDisplay(BindingResult result){
        Iterator<FieldError> errorList = result.getFieldErrors().iterator();
        List<String> s = new ArrayList<>();
        while (errorList.hasNext()){
            FieldError error = errorList.next();
            s.add(error.getField() + ": " + error.getDefaultMessage());
        }

        Iterator<ObjectError> globalErrorList = result.getGlobalErrors().iterator();
        while (globalErrorList.hasNext()){
            s.add(globalErrorList.next().getDefaultMessage());
        }
        return s;
    }


}
