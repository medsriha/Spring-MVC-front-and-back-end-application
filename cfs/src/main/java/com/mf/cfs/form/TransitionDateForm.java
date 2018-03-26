package com.mf.cfs.form;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TransitionDateForm {

    @NotNull
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
