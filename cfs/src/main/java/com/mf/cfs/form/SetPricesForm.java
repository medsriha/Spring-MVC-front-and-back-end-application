package com.mf.cfs.form;

import com.mf.cfs.domain.Fund;
import com.mf.cfs.domain.FundPriceDetail;
import com.mf.cfs.domain.Fund_Price_History;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class SetPricesForm {

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    private List<FundPriceDetail> allFunds;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<FundPriceDetail> getAllFunds() {
        return allFunds;
    }

    public void setAllFunds(List<FundPriceDetail> allFunds) {
        this.allFunds = allFunds;
    }
}
