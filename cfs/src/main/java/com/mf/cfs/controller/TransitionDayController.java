package com.mf.cfs.controller;

import com.mf.cfs.dao.EmployeeDao;
import com.mf.cfs.domain.*;
import com.mf.cfs.form.CreateFundForm;
import com.mf.cfs.form.SetPricesForm;
import com.mf.cfs.form.TransitionDateForm;
import com.mf.cfs.service.AccountService;
import com.mf.cfs.service.FundService;
import com.mf.cfs.service.TransitionDayService;
import com.mf.cfs.util.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class TransitionDayController {

    @Autowired
    TransitionDayService transitionDayService;

    @Autowired
    FundService fundService;

    @Autowired
    AccountService accountService;

    @Autowired
    EmployeeDao employeeDao;

    private long day = 1000L * 60 * 60 * 24;

    @RequestMapping(value = "/employee/transition_day",method = RequestMethod.GET)
    public String loadTransitionDayForm(Model model)    {
        List<Fund> fundsList = fundService.getAllFunds();
        model.addAttribute("funds", fundsList);
        Date d = transitionDayService.getDate();
        Date nextDay = new Date();
        nextDay.setTime(d.getTime() + day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("date", sdf.format(d));
        model.addAttribute("nextDate", sdf.format(nextDay));
        model.addAttribute("form", new TransitionDateForm());
        return "transition_day";
    }

    @PostMapping("/employee/transition_day")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String transitionDayProcess(HttpServletRequest request, Principal principal,
                                       Model model,
                                       @ModelAttribute(value = "form") @Valid TransitionDateForm form,
                                       BindingResult result){

        if (result.hasErrors()) {
            model.addAttribute("form", new TransitionDateForm());
            System.out.println(result.getAllErrors());
            return "transition_day";
        }

        List<Fund> fundsList = fundService.getAllFunds();
        ConcurrentHashMap<Fund, String> hm = new ConcurrentHashMap<>();
        for(Fund fund : fundsList)  {
            if(request.getParameter(String.valueOf(fund.getFund_id())) != null) {
                hm.put(fund, request.getParameter(String.valueOf(fund.getFund_id())));
                //System.out.println(request.getParameter(String.valueOf(fund.getFund_id())));
            }
        }

        boolean out = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(form.getDate());
            out = transitionDayService.performTransitionDay(d, fundsList, hm, result);
            if(out == true) {
                model.addAttribute("reminder", Reminder.TRANSITION_DAY_SUCCESS.getReminder());
                return "reminder";
            }   else    {
                Date old_d = transitionDayService.getDate();
                model.addAttribute("date", sdf.format(old_d));
                model.addAttribute("funds", fundsList);
                return "transition_day";
            }
        }   catch(ParseException e) {
            Date d = transitionDayService.getDate();
            model.addAttribute("date", sdf.format(d));
            model.addAttribute("funds", fundsList);
            result.addError(new ObjectError("Format", "Invalid Date Format"));
            return "transition_day";
        }


    }
}
