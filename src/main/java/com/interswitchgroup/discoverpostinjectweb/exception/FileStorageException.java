package com.interswitchgroup.discoverpostinjectweb.exception;

public class FileStorageException extends BaseException {

    public FileStorageException() {
        super();
    }

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
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
