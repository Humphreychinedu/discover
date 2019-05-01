package com.interswitchgroup.discoverpostinjectweb.exception;

public class InvalidOperationException extends BaseException {

    private String message;
    public InvalidOperationException(String message){
        this.message = message;
    }
    @Override
    public String getMessageCode() {
        return message;
    }

    @Override
    public Object[] getMessageArguments() {
        return new Object[0];
    }
}
