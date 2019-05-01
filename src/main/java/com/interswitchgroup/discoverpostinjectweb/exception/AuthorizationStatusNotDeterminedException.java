package com.interswitchgroup.discoverpostinjectweb.exception;

import org.springframework.security.access.AccessDeniedException;

public class AuthorizationStatusNotDeterminedException extends AccessDeniedException implements I18n {

    private String logKey = ExceptionUtil.generateErrorLogKey();

    public AuthorizationStatusNotDeterminedException(String message) {
        super(message);
    }

    public AuthorizationStatusNotDeterminedException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getLogKey() {
        return logKey;
    }

    @Override
    public String getMessageCode() {
        return "authorization.status.not.determined";
    }

    @Override
    public Object[] getMessageArguments() {
        return new Object[]{logKey};
    }
}
