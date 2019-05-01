package com.interswitchgroup.discoverpostinjectweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestNotValidException extends BaseException {

    public RequestNotValidException(String message) {
        super(message);
    }

    public RequestNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
