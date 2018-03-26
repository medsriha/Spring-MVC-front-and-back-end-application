package com.mf.cfs.domain;

import com.mf.cfs.form.DepositCheckForm;
import java.util.List;

public class CustomerDetail {
    private List<TransactionForDisplay> transactionForDisplays;
    private Customer customer;
    private List<PositionForDisplay> positions;

    public List<TransactionForDisplay> getTransactionForDisplays() {
        return transactionForDisplays;
    }

    public void setTransactionForDisplays(List<TransactionForDisplay> transactionForDisplays) {
        this.transactionForDisplays = transactionForDisplays;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<PositionForDisplay> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionForDisplay> positions) {
        this.positions = positions;
    }
}
