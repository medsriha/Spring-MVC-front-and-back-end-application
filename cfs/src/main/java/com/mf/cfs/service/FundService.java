package com.mf.cfs.service;

import com.mf.cfs.domain.Fund;
import com.mf.cfs.domain.FundResearch;
import com.mf.cfs.domain.Fund_Price_History;
import com.mf.cfs.form.CreateFundForm;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Date;

public interface FundService {
    public boolean createFund(CreateFundForm form, BindingResult result);
    boolean createFundPrice(Fund_Price_History fund_price);
    List<Fund> getAllFunds();
    List<Fund> getFundsByCusId(int customer_id);
    double getPrice(Date date, Fund fund);
    List<FundResearch> getFundResearch();
}
