package com.mf.cfs.domain;

import javax.persistence.*;

@Entity
public class Fund_Price_History {

    @EmbeddedId
    @Column(nullable = false)
    private FundDateCompPK id;


    @Column(nullable = false, precision=16, scale=2)
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FundDateCompPK getId() {
        return id;
    }

    public void setId(FundDateCompPK id) {
        this.id = id;
    }

}
