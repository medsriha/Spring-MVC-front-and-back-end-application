package com.mf.cfs.controller;

import com.mf.cfs.dao.CustomerDao;
import com.mf.cfs.domain.Customer;
import com.mf.cfs.form.LoginForm;
import com.mf.cfs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private CustomerDao customerDao;

    //Display login page - Shared page between Customer and Employee
    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        System.out.println("Login Error");
        return "login";
    }

    @GetMapping("/logout")
    public String afterLogout(Model model) {
        model.addAttribute("logout", true);
        return "login";
    }

}
