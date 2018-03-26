package com.mf.cfs.form;

import com.mf.cfs.validation.ValidMatchingPassword;
import com.mf.cfs.validation.ValidPassword;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ValidMatchingPassword
public class RegularPasswordForm {
    private List<String> errMsg = new ArrayList<>();

    public void setErrMsg(List<String> s){
        errMsg.addAll(s);
    }

    public void setErrMsg(String s){
        errMsg.add(s);
    }

    public List<String> getErrMsg(){
        return errMsg;
    }

    @NotEmpty
    @NotNull
    private String currentPassWd;

    @NotEmpty
    @NotNull
    @ValidPassword
    private String newPassWd;

    @NotEmpty
    @NotNull
    private String matchingPassWd;

    public String getCurrentPassWd() {
        return currentPassWd;
    }

    public void setCurrentPassWd(String currentPassWd) {
        this.currentPassWd = currentPassWd;
    }

    public String getNewPassWd() {
        return newPassWd;
    }

    public void setNewPassWd(String newPassWd) {
        this.newPassWd = newPassWd;
    }

    public String getMatchingPassWd() {
        return matchingPassWd;
    }

    public void setMatchingPassWd(String matchingPassWd) {
        this.matchingPassWd = matchingPassWd;
    }

}
