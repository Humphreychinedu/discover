package com.interswitchgroup.discoverpostinjectweb.exception;

public class ApplicationException extends BaseException {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    public String getMessageCode() {
        return "server.error";
    }

    @Override
    public Object[] getMessageArguments() {
        return null;
    }
}
