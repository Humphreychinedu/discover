package com.interswitchgroup.discoverpostinjectweb.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateTransactionRequest {

    @NotBlank
    private String filename;
    @NotBlank
    private String initiator;
    @NotBlank
    private String dateConverted;

    @NotBlank
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
}
