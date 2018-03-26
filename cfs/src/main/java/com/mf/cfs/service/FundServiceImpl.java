package com.mf.cfs.service;

import com.mf.cfs.dao.*;

import com.mf.cfs.domain.Fund;
import com.mf.cfs.domain.FundDateCompPK;
import com.mf.cfs.domain.Fund_Price_History;
import com.mf.cfs.domain.Position;
import com.mf.cfs.domain.*;
import com.mf.cfs.form.CreateFundForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service(value = "FundService")
public class FundServiceImpl implements FundService {

    @Autowired
    private FundDao fundDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private FundPriceHistoryDao fund_price_historyDao;

    @Autowired
    private LastDateDao lastDateDao;


    public boolean isValidFund(CreateFundForm form, BindingResult result)   {

        Fund existingFund = fundDao.findFundByName(form.getFundName());

        if(existingFund != null)    {
            result.addError(new ObjectError("form", "Fund with the name already exists"));
            return false;
        }

        existingFund = fundDao.findFundBySymbol(form.getFundSymbol());
        if(existingFund != null)    {
            result.addError(new ObjectError("form", "Fund with the symbol already exists"));
            return false;
        }

        return true;
    }



    @Override
    public boolean createFund(CreateFundForm form, BindingResult result) {

        if(!isValidFund(form, result))  {
            return false;
        }
        //fundDao.save(fund);
        Fund newFund = new Fund();
        newFund.setSymbol(form.getFundSymbol().toUpperCase());
        newFund.setName(form.getFundName());
        newFund.setPrice(0.0);

        List<LastDate> ld = lastDateDao.findAll();
        LastDate lastDate = ld.get(ld.size()-1);

        System.out.println(lastDate.getLastDate());
        newFund.setCreated_at(lastDate.getLastDate());
        System.out.println(newFund.getCreated_at());
        fundDao.save(newFund);
        return true;
    }

    @Override
    public boolean createFundPrice(Fund_Price_History fund_price) {
        return false;
    }

    @Override
    public List<Fund> getAllFunds() {
        return fundDao.findAll();
    }

    @Override
    public List<Fund> getFundsByCusId(int customer_id) {
        List<Fund> ans = new ArrayList<>();
        List<Position> positions = positionDao.findByIdCustomer(customerDao.findOne(customer_id));
        for (Position p : positions) {
            ans.add(p.getId().getFund());
        }
        return ans;
    }

    @Override
    public double getPrice(Date date, Fund fund) {
        Fund_Price_History fph = fund_price_historyDao.getById(new FundDateCompPK(fund, date));
        if (fph == null) {
            return 0;
        }
        return fund_price_historyDao.getById(new FundDateCompPK(fund, date)).getPrice();
    }
    @Override
    public List<FundResearch> getFundResearch() {
        List<Fund> funds = getAllFunds();
        List<FundResearch> researchs = new ArrayList<>();
        for (Fund fund: funds) {
            List<Fund_Price_History> histories = fund_price_historyDao.getByIdFund(fund);
            researchs.add(new FundResearch(fund, histories));
        }
        return researchs;
    }

}
