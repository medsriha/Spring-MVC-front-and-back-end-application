package com.mf.cfs.form;

import com.mf.cfs.domain.Customer;
import com.mf.cfs.domain.Fund;
import org.hibernate.validator.constraints.Range;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class SellFundForm {

    private List<Fund> fundList;

    @NotNull
    private int fund_id;

    @Range(min = (long)0.001, max = 10000, message = "Please enter a value between 0.001 and 10000.")
    @Valid
    private double shares;

    private Date timestamp;

    public SellFundForm() {
    }

    public SellFundForm(List<Fund> fundList) {
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

    public double getShares() {
        return shares;
    }

    public void setShares(double amount) {
        this.shares = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
