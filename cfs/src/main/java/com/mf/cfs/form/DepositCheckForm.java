package com.mf.cfs.form;

import com.mf.cfs.domain.Customer;
import com.mf.cfs.domain.Fund;
import org.hibernate.validator.constraints.Range;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

public class DepositCheckForm {
    @Range(min = 1, max = 100000, message = "Please enter a value between $1 and $100,000.")
    @Valid
    private double amount;

    @NotNull
    private String username;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}