package com.interswitchgroup.discoverpostinjectweb.exception;

public class UploadsLimitExceededException extends BaseException{

    private String exMessage;
    public UploadsLimitExceededException(String exMessage){
        super();
        this.exMessage = exMessage;
    }

    @Override
    public String getMessageCode() {
        return "upload.exceeded";
    }

    @Override
    public Object[] getMessageArguments() {
        return new Object[]{exMessage};
    }
}
