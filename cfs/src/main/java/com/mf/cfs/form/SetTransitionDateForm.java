package com.mf.cfs.form;

import com.mf.cfs.domain.Fund;
import com.mf.cfs.domain.Fund_Price_History;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class SetTransitionDateForm {

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
