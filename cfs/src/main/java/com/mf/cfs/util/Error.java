package com.mf.cfs.util;

public enum Error {
    INVALID_CUSTOMER_ID("Oops! It seems that you somehow changed the customer ID."),
    ALREADY_REGISTERED_CUSTOMER("You have already created an account with this email address!"),
    ALREADY_REGISTERED_EMPLOYEE("You have already created an account with this email address!"),
    WRONG_PASSWORD("Your input password is incorrect, try again!");

    private String error;

    public String getError() {
        return error;
    }

    Error(String error){
        this.error = error;
    }
}
