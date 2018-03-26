package com.mf.cfs.form;

import com.mf.cfs.validation.ValidEmail;
import com.mf.cfs.validation.ValidMatchingPassword;
import com.mf.cfs.validation.ValidPassword;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ValidMatchingPassword
public class CreateEmployeeForm {
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
    @ValidEmail
    private String email;

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    private String matchingPassword;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
