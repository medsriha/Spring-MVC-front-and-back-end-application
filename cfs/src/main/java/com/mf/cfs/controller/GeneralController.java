package com.mf.cfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {

    @RequestMapping("/error")
    public String show404() {
        return "404";
    }

    @RequestMapping("/unauthorized")
    public String show401() {
        return "401";
    }

    @RequestMapping("/internal_failure")
    public String show500() {
        return "500";
    }
}
