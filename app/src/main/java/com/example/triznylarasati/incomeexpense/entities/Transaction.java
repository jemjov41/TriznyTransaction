package com.example.triznylarasati.incomeexpense.entities;

/**
 * Created by Jemmy on 11/7/2017.
 */

public class Transaction {

    protected static final String nameDefault = null;

    protected String name = nameDefault;

    protected static final String amountDefault = null;

    protected String amount = amountDefault;

    protected static final String createdOnDefault = null;

    protected String createdOn = createdOnDefault;

    protected static final String flagDefault = null;

    protected String flag = flagDefault;

    public Transaction() {
        super();
    }

    public Transaction(String name, String amount, String createdOn, String flag) {
        this.name = name;
        this.amount = amount;
        this.createdOn = createdOn;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
