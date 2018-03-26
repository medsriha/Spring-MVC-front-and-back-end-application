package com.mf.cfs.dao;

import com.mf.cfs.domain.Customer;
import com.mf.cfs.domain.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


public interface FundDao extends JpaRepository<Fund, Integer> {
    Fund findFundBySymbol(String symbol);
    Fund findFundByName(String name);
}
