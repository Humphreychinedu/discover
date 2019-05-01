package com.interswitchgroup.discoverpostinjectweb.passport;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PassportBadRequestResponse {

    private String code;
    private String description;
    private List<PassportFieldError> errors;
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_reason")
    private String errorReason;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PassportFieldError> getErrors() {
        return this.errors;
    }

    public void setErrors(List<PassportFieldError> errors) {
        this.errors = errors;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorReason() {
        return this.errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
