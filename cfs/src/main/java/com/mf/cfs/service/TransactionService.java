package com.mf.cfs.service;

import com.mf.cfs.domain.*;
import com.mf.cfs.form.BuyFundForm;
import com.mf.cfs.form.DepositCheckForm;
import com.mf.cfs.form.RequestCheckForm;
import com.mf.cfs.form.SellFundForm;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
public interface TransactionService {
    void createData();
    double getCash(int id);
    boolean hasSufficientCash(Customer customer, double amount, BindingResult result);
    boolean isValidFund(Fund fund, BindingResult result);
    Transaction sellFund(int id, SellFundForm form, BindingResult result);
    Transaction buyFund(int id, BuyFundForm form, BindingResult result);
    Transaction depositCheck(DepositCheckForm form, BindingResult result);
    Transaction requestCheck(int id, RequestCheckForm form, BindingResult result);
    void approveCheckDeposit(Date date, Transaction transaction);
    void approveSellFund(Date date, Transaction transaction);
    void approveCheckRequest(Date date, Transaction transaction);
    void approveBuyFund(Date date, Transaction transaction);
    List<TransactionForDisplay> getTransactions(int customer_id);
}
