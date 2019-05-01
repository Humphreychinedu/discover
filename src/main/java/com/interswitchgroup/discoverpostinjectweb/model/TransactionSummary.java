package com.interswitchgroup.discoverpostinjectweb.model;

import java.util.Objects;

public class TransactionSummary extends BaseEntity {

    private String transactionType;
    private String transactionAmount;
    private String transactionLocation;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionLocation() {
        return transactionLocation;
    }

    public void setTransactionLocation(String transactionLocation) {
        this.transactionLocation = transactionLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransactionSummary that = (TransactionSummary) o;
        return Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(transactionAmount, that.transactionAmount) &&
                Objects.equals(transactionLocation, that.transactionLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transactionType, transactionAmount, transactionLocation);
    }

    @Override
    public String toString() {
        return "TransactionSummary{" +
                "transactionType='" + transactionType + '\'' +
                ", transactionAmount='" + transactionAmount + '\'' +
                ", transactionLocation='" + transactionLocation + '\'' +
                '}';
    }
}
