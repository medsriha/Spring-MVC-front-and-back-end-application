package com.mf.cfs.controller;

import com.mf.cfs.form.CreateCustomerForm;
import com.mf.cfs.form.CreateEmployeeForm;
import com.mf.cfs.util.Reminder;
import com.mf.cfs.service.AccountService;
import com.mf.cfs.validation.ValidMatchingPassword;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class CreateAccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/employee/new-employee", method = RequestMethod.GET)
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employee", new CreateEmployeeForm());
        return "create_new_employee";
    }

    @RequestMapping(value = "/employee/new-customer", method = RequestMethod.GET)
    public String showCreateCustomerForm(Model model) {
        model.addAttribute("customer", new CreateCustomerForm());
        return "create_new_customer";
    }

    @RequestMapping(value = "/employee/new-employee.do", method = RequestMethod.POST)
    public String createEmployee(Model model,
                                 @ModelAttribute(value = "form") @Valid CreateEmployeeForm form,
                                 BindingResult result){
        boolean isSuccess = false;
        // error occurs when failing to meet the requirement of annotation defined by form object
        if (!result.hasErrors()) {
            try {
                isSuccess = accountService.createEmployeeAccount(form);
            } catch(RuntimeException e){
                // error occurs when querying the database and failing to meet the criterion
                form.setErrMsg(e.getMessage());
                model.addAttribute("employee", form);
                return "create_new_employee";
            }
        }
        // successfully created
        if (isSuccess) {
            model.addAttribute("reminder", Reminder.CREATE_EMPLOYEE_ACCOUNT_SUCCESS.getReminder());
            return "reminder";
        }
        form.setErrMsg(errorDisplay(result));
        model.addAttribute("employee", form);
        return "create_new_employee";
    }

    @RequestMapping(value = "/employee/new-customer.do", method = RequestMethod.POST)
    public String createCustomer(Model model,
                                 @ModelAttribute(value = "form") @Valid CreateCustomerForm form,
                                 BindingResult result){
        boolean isSuccess = false;
        System.out.println(form.getEmail());
        System.out.println(form.getPassword());
        System.out.println(form.getMatchingPassword());
        if (!result.hasErrors()) {
            System.out.println("no binding error");
            try{
                isSuccess = accountService.createCustomerAccount(form);
            } catch(RuntimeException e){
                System.out.println("run time exception");
                System.out.println(e.getMessage());
                form.setErrMsg(e.getMessage());
                model.addAttribute("customer", form);
                return "create_new_customer";
            }
        }
        if (isSuccess){
            System.out.println("success");
            model.addAttribute("reminder", Reminder.CREATE_CUSTOMER_ACCOUNT_SUCCESS.getReminder());
            return "reminder";
        }
        System.out.println("binding error");
        form.setErrMsg(errorDisplay(result));
        model.addAttribute("customer",form);
        return "create_new_customer";
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
