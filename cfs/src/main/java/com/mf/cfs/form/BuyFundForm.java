package com.mf.cfs.form;

import com.mf.cfs.domain.Fund;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class BuyFundForm {

    private List<Fund> fundList;

    @NotNull
    private int fund_id;

    @Range(min = 1, max = 1000000, message = "Please enter a value between $1 and $1 million.")
    @Valid
    private double amount;

    private Date timestamp;

    public BuyFundForm() {
    }

    public BuyFundForm(List<Fund> fundList) {
        this.fundList = fundList;
    }

    public List<Fund> getFundList() {
        return fundList;
    }

    public void setFundList(List<Fund> fundList) {
        this.fundList = fundList;
    }

    public int getFund_id() {
        return fund_id;
    }

    public void setFund_id(int fund_id) {
        this.fund_id = fund_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
