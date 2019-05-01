package com.interswitchgroup.discoverpostinjectweb.model;

import java.util.Objects;

public class Transaction extends BaseEntity{

    private String filename;
    private String initiator;
    private String dateConverted;
    private String totalTransaction;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getDateConverted() {
        return dateConverted;
    }

    public void setDateConverted(String dateConverted) {
        this.dateConverted = dateConverted;
    }

    public String getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(String totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(filename, that.filename) &&
                Objects.equals(initiator, that.initiator) &&
                Objects.equals(dateConverted, that.dateConverted) &&
                Objects.equals(totalTransaction, that.totalTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, initiator, dateConverted, totalTransaction);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "filename='" + filename + '\'' +
                ", initiator='" + initiator + '\'' +
                ", dateConverted='" + dateConverted + '\'' +
                ", totalTransaction='" + totalTransaction + '\'' +
                '}';
    }
}
