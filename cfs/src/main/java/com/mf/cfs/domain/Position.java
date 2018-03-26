package com.mf.cfs.domain;

import javax.persistence.*;

@Entity
public class Position {
    @EmbeddedId
    @Column(nullable = false)
    private PositionCompPK id;


    @Column(nullable = false, precision=16, scale=3)
    private double shares;

    public double getShares() {
        return shares;
    }

    public void setShares(double shares) {
        this.shares = shares;
    }

    public PositionCompPK getId() {
        return id;
    }

    public void setId(PositionCompPK id) {
        this.id = id;
    }
}
