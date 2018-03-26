package com.mf.cfs.dao;

import com.mf.cfs.domain.Fund;
import com.mf.cfs.domain.FundDateCompPK;
import com.mf.cfs.domain.Fund_Price_History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FundPriceHistoryDao extends JpaRepository<Fund_Price_History, Integer> {
    @Query("select MAX(date) from Fund_Price_History")
    Date getMaxDate();
    List<Fund_Price_History> getByIdFund(Fund fund);
    List<Fund_Price_History> getByIdDate(Date date);
    Fund_Price_History getById(FundDateCompPK id);

}
