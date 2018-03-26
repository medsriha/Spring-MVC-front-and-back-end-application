package com.mf.cfs.form;

import com.mf.cfs.validation.ValidEmail;
import com.mf.cfs.validation.ValidMatchingPassword;
import com.mf.cfs.validation.ValidPassword;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ValidMatchingPassword
public class SpecialPasswordForm {
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

    @NotNull
    private int userID;

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    private String matchingPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
