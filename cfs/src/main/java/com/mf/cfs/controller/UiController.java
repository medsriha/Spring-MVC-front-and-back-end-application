package com.mf.cfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UiController {

    //Mutual Fund Dashboard - This page includes also the Buy Fund use case.
    @GetMapping("/uimutual_funds")
    public String showMutualFunds(){return "mutual_funds";}

    // Main Dashboard - This pages includes user address and transaction history use cases.
    @GetMapping("/uidashboard")
    public String showDashboard(){return "dashboard";}

    @GetMapping("/uiSell_fund")
    public String showsellFund(){return "sell_fund";}

    @GetMapping("/uichange_password")
    public String showchangePass(){return "change_password";}

    @GetMapping("/ui/404")
    public String show404Error(){return "404";}

    @GetMapping("/ui500")
    public String show500Error(){return "500";}

    //Employee

    @GetMapping("/uiemployee_dashboard")
    public String showEmployeeDashboard(){return "employee_dashboard";}

    @GetMapping("/uichange_employee_password")
    public String showEmployeeChangePass(){return "change_employee_password";}


    @GetMapping("/uicreate_new_customer")
    public String showCreateCustomer(){return "create_new_customer";}

    @GetMapping("/uicreate_new_employee")
    public String showCreateemployee(){return "create_new_employee";}

    @GetMapping("/uitransition_day")
    public String showTransitionDay(){return "transition_day";}


}
