package com.mf.cfs.domain;


import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int transaction_id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
    @JoinColumn(name="id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Fund.class)
    @JoinColumn(name="fund_id")
    private Fund fund;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date executeDate;

    @Column(precision=16, scale=3)
    private double shares;

    @Column(precision=16, scale=2)
    private double amount;

    @Column(nullable = false)
    private Transaction_type transaction_type;

    public Transaction() {
    }

    // Constructor for sell funds
    public Transaction(Customer customer) {
        this.customer = customer;
    }


    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }


    public double getShares() {
        return shares;
    }

    public void setShares(double shares) {
        this.shares = shares;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Transaction_type getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(Transaction_type transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

}
