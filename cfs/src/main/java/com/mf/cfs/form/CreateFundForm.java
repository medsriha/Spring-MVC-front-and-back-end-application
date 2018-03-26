package com.mf.cfs.form;

import com.mf.cfs.domain.Customer;
import com.mf.cfs.domain.Fund;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

public class CreateFundForm {

    @NotNull
    @Size(min=2, max=30)
    private String fundName;

    @NotNull
    @Size(min=2, max=6)
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String fundSymbol;

    public CreateFundForm() {
    }


    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundSymbol() {
        return fundSymbol;
    }

    public void setFundSymbol(String fundSymbol) {
        this.fundSymbol = fundSymbol;
    }

}
