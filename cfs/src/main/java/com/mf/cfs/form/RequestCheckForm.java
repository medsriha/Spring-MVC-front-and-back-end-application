package com.mf.cfs.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public class RequestCheckForm {
    @Range(min = 1, max = 100000, message = "Please enter a value between $1 and $100,000.")
    @Valid
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
