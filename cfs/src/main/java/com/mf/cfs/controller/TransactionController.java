package com.mf.cfs.controller;

import com.mf.cfs.dao.CustomerDao;
import com.mf.cfs.domain.*;
import com.mf.cfs.form.*;
import com.mf.cfs.form.RequestCheckForm;
import com.mf.cfs.service.AccountService;
import com.mf.cfs.service.FundService;
import com.mf.cfs.service.TransactionService;
import com.mf.cfs.service.TransitionDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class
TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransitionDayService transitionDayService;
    @Autowired
    private FundService fundService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerDao customerDao;
    @GetMapping("/template")
    public String showTemplate() {
        return "template";
    }

    @GetMapping("/init")
    public String init() {
            transactionService.createData();
            return "redirect:/login";
    }

    private String redirectDashboard(Principal principal, Model model) {
        int id = Integer.parseInt(principal.getName());

        List<TransactionForDisplay> transactions = transactionService.getTransactions(id);
        List<PositionForDisplay> positions = accountService.getPositionForDisplays(id);
        Customer customer = accountService.findCustomer(id);
        Date lastTradingDay = transitionDayService.getDate();

        model.addAttribute("date", lastTradingDay);
        model.addAttribute("transactions", transactions);
        model.addAttribute("positions", positions);
        model.addAttribute("customer", customer);
        return "dashboard";
    }

    @PostMapping("/customer/request_check")
    @Transactional
    public String requestCheck(Principal principal,
                               Model model,
                               @ModelAttribute(value = "form") @Valid RequestCheckForm form,
                               BindingResult result) {
        int id = Integer.parseInt(principal.getName());
        Transaction transaction = transactionService.requestCheck(id, form, result);
        model.addAttribute("sellForm", new SellFundForm());

        if (result.hasErrors()) {
            return redirectDashboard(principal, model);
        }

        model.addAttribute("transaction", transaction);
        return redirectDashboard(principal, model);
    }

    @GetMapping(value = {"/customer/buy_fund", "/customer/request_check", "/customer/research_buy_fund"})
    public String displayDashboard() {
        return "redirect:/dashboard";
    }

    @PostMapping("/buy_fund")
    @Transactional
    public String buyFund(Principal principal,
                          Model model,
                          @ModelAttribute(value = "form") @Valid BuyFundForm form,
                          BindingResult result) {
        int id = Integer.parseInt(principal.getName());
        Transaction transaction = transactionService.buyFund(id, form, result);

        if (result.hasErrors()) {
            form.setFundList(fundService.getAllFunds());
            result.addError(new ObjectError("form", "Your request didn't go through"));
            return "form_buy_fund";
        }

        model.addAttribute("transaction", transaction);
        return redirectDashboard(principal, model);
    }

    @PostMapping("/customer/research_buy_fund")
    @Transactional
    public String researchBuyFund(Principal principal,
                          Model model,
                          @ModelAttribute(value = "form") @Valid BuyFundForm form,
                          BindingResult result) {
        int id = Integer.parseInt(principal.getName());
        Transaction transaction = transactionService.buyFund(id, form, result);

        if (result.hasErrors()) {
            form.setFundList(fundService.getAllFunds());
            model.addAttribute("customer", customerDao.findOne(id));
            model.addAttribute("researches", fundService.getFundResearch());
            result.addError(new ObjectError("form", "Your request didn't go through"));
            return "mutual_funds";
        }
        model.addAttribute("transaction", transaction);
        model.addAttribute("researches", fundService.getFundResearch());
        model.addAttribute("customer", customerDao.findOne(id));
        return "mutual_funds";
    }

    @PostMapping("/customer/sell_fund")
    @Transactional
    public String sellFund(Principal principal,
                          Model model,
                          @ModelAttribute(value = "sellForm") @Valid SellFundForm form,
                          BindingResult result) {
        Transaction transaction = transactionService.sellFund(Integer.parseInt(principal.getName()), form, result);
        model.addAttribute("form", new RequestCheckForm());

        if (result.hasErrors()) {
            return redirectDashboard(principal, model);
        }

        model.addAttribute("transaction", transaction);
        return redirectDashboard(principal, model);
    }

    @PostMapping("/employee/deposit_check")
    @Transactional
    public String depositCheck(Model model, @ModelAttribute(value="form") @Valid DepositCheckForm form, BindingResult bindingResult) {
        Customer customer = customerDao.findByUsername(form.getUsername());
        if (customer == null) {
            return "redirect:/employee/dashboard";
        }
        Transaction transaction = transactionService.depositCheck(form, bindingResult);
        int id = customer.getId();
        if (transaction == null) {
            CustomerDetail customerDetail = new CustomerDetail();
            customerDetail.setTransactionForDisplays(transactionService.getTransactions(customer.getId()));
            customerDetail.setCustomer(customer);
            customerDetail.setPositions(accountService.getPositionForDisplays(customer.getId()));
            form.setUsername(customer.getUsername());
            model.addAttribute("form", form);
            model.addAttribute("customerDetail", customerDetail);
            model.addAttribute("specialPassWd", new SpecialPasswordForm());
            return "customer_view_account";
        }

        return "redirect:/employee/customer_account/" + id;
    }

    @GetMapping("transition_date")
    public String showSetTransitionDateForm(Model model) {
        model.addAttribute("form", new SetPricesForm());
        return "form_set_transition_date";
    }

    /*@PostMapping("transition_date")
    @Transactional
    public String SetTransitionDate(Model model, @ModelAttribute(value="form") @Valid SetPricesForm form,
                                    BindingResult bindingResult) {
        transitionDayService.approveTransactions(form.getDate(), bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return "form_set_transition_date";
        }
        return "success";
    }*/
}
