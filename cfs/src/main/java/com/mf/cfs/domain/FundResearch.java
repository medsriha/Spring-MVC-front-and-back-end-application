package com.mf.cfs.domain;

import java.util.List;

public class FundResearch {
    private Fund fund;
    private List<Fund_Price_History> history;

    public FundResearch(Fund fund, List<Fund_Price_History> history) {
        this.fund = fund;
        this.history = history;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public List<Fund_Price_History> getHistory() {
        return history;
    }

    public void setHistory(List<Fund_Price_History> history) {
        this.history = history;
    }
}
