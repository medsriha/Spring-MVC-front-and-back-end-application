package com.mf.cfs.form;

import com.mf.cfs.validation.ValidEmail;
import com.mf.cfs.validation.ValidMatchingPassword;
import com.mf.cfs.validation.ValidPassword;
import com.mf.cfs.validation.ValidZipCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ValidMatchingPassword
public class CreateCustomerForm {
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

    @ValidPassword
    @NotEmpty
    private String password;

    private String matchingPassword;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @NotEmpty
    private String addr_line1;

    private String addr_line2;

    @NotEmpty
    private String city;

    @NotEmpty
    private String state;

    @NotEmpty
    @ValidZipCode
    private String zip;

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

    public String getAddr_line1() {
        return addr_line1;
    }

    public void setAddr_line1(String addr_line1) {
        this.addr_line1 = addr_line1;
    }

    public String getAddr_line2() {
        return addr_line2;
    }

    public void setAddr_line2(String addr_line2) {
        this.addr_line2 = addr_line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
