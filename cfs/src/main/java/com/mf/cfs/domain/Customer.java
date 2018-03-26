package com.mf.cfs.domain;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String addrLine1;

    @Column
    private String addrLine2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, precision=16, scale=2)
    @Value("${some.key:0.00}")
    private Double cash;

    @Column(nullable = false, precision=16, scale=2)
    @Value("${some.key:0.00}")
    private Double availableCash;

    @Column(nullable = false)
    private String role;

    public Customer()   {
        this.cash = 0.00;
        this.availableCash = 0.00;
        this.role="Customer";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddrLine1() {
        return addrLine1;
    }

    public void setAddrLine1(String addr_line1) {
        this.addrLine1 = addr_line1;
    }

    public String getAddrLine2() {
        return addrLine2;
    }

    public void setAddrLine2(String addr_line2) {
        this.addrLine2 = addr_line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(Double availableCash) {
        this.availableCash = availableCash;
    }
}
