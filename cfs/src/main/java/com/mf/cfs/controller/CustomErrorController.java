package com.mf.cfs.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController{

    private static final String PATH = "/error";

    @RequestMapping(value = PATH, method = RequestMethod.GET)
    public String renderErrorPage(HttpServletRequest httpRequest, Model model) {
        return "404";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}

