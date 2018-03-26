package com.mf.cfs.util;

public enum Reminder {
    CREATE_EMPLOYEE_ACCOUNT_SUCCESS("Congrats! You've successfully created an employee account."),
    CREATE_CUSTOMER_ACCOUNT_SUCCESS("Congrats! You've successfully created a customer account."),
    INVALID_CUSTOMER_ID("Oops! It seems that you somehow changed the customer ID."),
    TRANSITION_DAY_SUCCESS("Congrats! You've successfully set prices for funds in the transition day you selected."),
    CHANGE_PASSWORD_SUCCESS("Congrats! You've successfully changed your password."),
    RESET_CUSTOMER_PASSWORD_SUCCESS("Congrats! You've successfully reset password for this customer."),
    CREATE_FUND_SUCCESS("Congrats, you have succesfully created a new Fund.");

    private String reminder;

    Reminder(String reminder){
        this.reminder = reminder;
    }

    public String getReminder(){
        return reminder;
    }
}
