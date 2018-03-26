package com.mf.cfs.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Embeddable
public class FundDateCompPK implements Serializable{

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Fund.class)
    @JoinColumn(name="fund_id")
    private Fund fund;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public FundDateCompPK(Fund fund, Date date) {
        this.fund = fund;
        this.date = date;
    }

    public FundDateCompPK(){}


    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
