package com.mf.cfs.service;

import com.mf.cfs.domain.Fund;
import com.mf.cfs.form.SetPricesForm;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface TransitionDayService {

    boolean approveTransactions(ConcurrentHashMap newPrices, Date date, BindingResult result);
    void setDate(Date date);
    Date getDate();
    boolean performTransitionDay(Date d, List<Fund> funds, ConcurrentHashMap<Fund, String> newPrices, BindingResult result);
    boolean updateAllFundPrices(Date d, List<Fund> funds, ConcurrentHashMap<Fund, String> hm, BindingResult result);
}
