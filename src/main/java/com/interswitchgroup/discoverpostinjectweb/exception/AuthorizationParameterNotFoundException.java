package com.interswitchgroup.discoverpostinjectweb.exception;

import org.springframework.security.access.AccessDeniedException;

public class AuthorizationParameterNotFoundException extends AccessDeniedException implements I18n  {

    private String parameter;
    private String parameterType;

    public AuthorizationParameterNotFoundException(String parameter, String parameterType) {
        super(String.format("Missing authorization %s: %s", parameterType, parameter));
        this.parameter = parameter;
        this.parameterType = parameterType;
    }

    public String getParameter() {
        return parameter;
    }

    public String getParameterType() {
        return parameterType;
    }

    @Override
    public String getMessageCode() {
        return "authorization.parameter.not.found";
    }

    @Override
    public Object[] getMessageArguments() {
        return new Object[]{parameterType, parameter};
    }
}
