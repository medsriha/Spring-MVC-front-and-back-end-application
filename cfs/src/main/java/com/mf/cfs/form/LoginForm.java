package com.mf.cfs.form;

import com.mf.cfs.validation.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;

public class LoginForm {
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;
//
//    @Value("${some.key:true}")
//    private boolean isCustomer;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//
//    public boolean isCustomer() {
//        return isCustomer;
//    }
//
//    public void setCustomer(boolean customer) {
//        isCustomer = customer;
//    }
}
