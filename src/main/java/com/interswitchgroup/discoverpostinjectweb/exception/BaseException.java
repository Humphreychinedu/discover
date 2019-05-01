package com.interswitchgroup.discoverpostinjectweb.exception;


public class BaseException extends RuntimeException implements I18n {

    protected BaseException() {
        super();
    }

    protected BaseException(String message) {
        super(message);
    }

    protected BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BaseException(Throwable cause) {
        super(cause);
    }


    @Override
    public String getMessageCode() {
        return null;
    }

    @Override
    public Object[] getMessageArguments() {
        return new Object[0];
    }
}
