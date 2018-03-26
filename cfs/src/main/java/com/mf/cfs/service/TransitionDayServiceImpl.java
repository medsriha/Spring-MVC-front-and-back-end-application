package com.mf.cfs.service;

import com.mf.cfs.dao.FundDao;
import com.mf.cfs.dao.FundPriceHistoryDao;
import com.mf.cfs.dao.LastDateDao;
import com.mf.cfs.dao.TransactionDao;
import com.mf.cfs.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service(value = "TransitionDayService")
public class TransitionDayServiceImpl implements TransitionDayService {
    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FundPriceHistoryDao fundPriceHistoryDao;

    @Autowired
    private LastDateDao dateDao;

    @Autowired
    private FundDao fundDao;


    @Autowired
    private FundService fundService;

    public boolean isValidDate(Date d, BindingResult result)   {
        List<LastDate> ld = dateDao.findAll();
        LastDate lastDate = ld.get(ld.size()-1);
        //System.out.println("<<<<<In Here = " + lastDate.getLastDate().toString() + ">>>>>>");
        if(lastDate.getLastDate().after(d) || lastDate.getLastDate().equals(d)) {
            result.addError(new ObjectError("form", "Please enter date after last transition day."));
            return false;
        }
        return true;
    }

    public boolean isValidFundList(ConcurrentHashMap<Fund, String> newPrices, BindingResult result)  {
        List<Fund> newFundList = fundService.getAllFunds();
        if (newFundList.size() != newPrices.keySet().size()) {
            result.addError(new ObjectError("form", "Fund list changed. Update Prices again"));
            return false;
        }

        for(Fund f : newFundList)   {
            if(!newPrices.containsKey(f))  {
                result.addError(new ObjectError("form", "New Fund Added. Update Prices again"));
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateAllFundPrices(Date d, List<Fund> funds, ConcurrentHashMap<Fund, String> hm, BindingResult result) {

        HashMap<Fund, Double> priceMap = new HashMap<>();

        try {
            for(Fund f: hm.keySet())    {
                double newPrice = Double.parseDouble(hm.get(f));
                priceMap.put(f, newPrice);
            }

        } catch(NumberFormatException e)  {
            result.addError(new ObjectError("form", "Please enter a valid price."));
            e.printStackTrace();
            return false;
        }

        for(Fund fund : priceMap.keySet())  {
            double newPrice = priceMap.get(fund);
            if(newPrice > 10000.00 || newPrice < 1.00)    {
                result.addError(new ObjectError("form", "Please enter a valid price in the range of $1 to $10000"));
                return false;
            }

        }
        for(Fund fund : priceMap.keySet())  {
            FundDateCompPK fpk = new FundDateCompPK(fund, d);
            Fund_Price_History fh = new Fund_Price_History();
            fh.setId(fpk);
            Double newPrice = priceMap.get(fund);
            fh.setPrice(newPrice);
            fundPriceHistoryDao.save(fh);
            fund.setPrice(newPrice);
            fundDao.save(fund);
        }

        return true;
    }

    @Override
    public void setDate(Date date) {

        LastDate ld = new LastDate();
        ld.setLastDate(date);
        System.out.println(ld.getLastDate());
        dateDao.save(ld);
    }

    @Override
    public Date getDate()   {
        List<LastDate> ld = dateDao.findAll();
        LastDate lastDate = ld.get(ld.size()-1);
        return lastDate.getLastDate();
    }

    @Override
    public boolean performTransitionDay(Date d, List<Fund> funds, ConcurrentHashMap<Fund, String> newPrices, BindingResult result)   {

        System.out.println("<<<<In Here>>>>>");
        if(!isValidDate(d, result)) {
            return false;
        }

        if(! isValidFundList(newPrices, result)) {
            return false;
        }

        if(!this.updateAllFundPrices(d, funds, newPrices, result))  {
            return false;
        }

        if(! this.approveTransactions(newPrices, d,result)) {
            return false;
        }

        this.setDate(d);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean approveTransactions(ConcurrentHashMap newPrices,Date date, BindingResult result) {

        List<Transaction> pendingTransactions = transactionDao.findByExecuteDateIsNull();
        for (Transaction t : pendingTransactions) {
            switch (t.getTransaction_type()) {
                case requestCheck:
                    transactionService.approveCheckRequest(date, t);
                    break;
                case depositCheck:
                    transactionService.approveCheckDeposit(date, t);
                    break;
                case buyFund:
                    transactionService.approveBuyFund(date, t);
                    break;
                case sellFund:
                    transactionService.approveSellFund(date, t);
            }
        }
        return true;
    }
}
