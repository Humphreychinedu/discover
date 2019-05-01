package com.interswitchgroup.discoverpostinjectweb.dto.response;

public class CreateTransactionResponse {

    private String status;
    private String message;

    public CreateTransactionResponse() {
    }

    public CreateTransactionResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
