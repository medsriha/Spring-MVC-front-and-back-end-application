package com.mf.cfs.domain;

import javax.persistence.*;
import java.io.*;
@Embeddable
public class PositionCompPK implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Fund.class)
    @JoinColumn(name="fund_id")
    private Fund fund;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
    @JoinColumn(name="id")
    private Customer customer;


    public PositionCompPK() { }
    public PositionCompPK(Customer customer, Fund fund) {
        this.setFund(fund);
        this.customer = customer;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }
}
