package com.interswitchgroup.discoverpostinjectweb.api;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ApiGenericResponse {

    private String statusCode;
    private String descriptionCode;

    public ApiGenericResponse() {
    }

    public ApiGenericResponse(String statusCode, String descriptionCode) {
        this.statusCode = statusCode;
        this.descriptionCode = descriptionCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public void setDescriptionCode(String descriptionCode) {
        this.descriptionCode = descriptionCode;
    }
}
