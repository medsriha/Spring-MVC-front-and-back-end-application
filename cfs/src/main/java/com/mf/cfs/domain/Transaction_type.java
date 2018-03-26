package com.mf.cfs.domain;

public enum Transaction_type {
    buyFund("Buy"),
    sellFund("Sell"),
    depositCheck("Deposit"),
    requestCheck("Withdraw");

    private final String name;

    private Transaction_type(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
