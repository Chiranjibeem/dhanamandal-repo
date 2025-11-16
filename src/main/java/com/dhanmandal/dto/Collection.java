package com.dhanmandal.dto;

public class Collection {

    public String name;
    public Double amount;
    public String transactionType;
    public String collectedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(String collectedBy) {
        this.collectedBy = collectedBy;
    }
}
