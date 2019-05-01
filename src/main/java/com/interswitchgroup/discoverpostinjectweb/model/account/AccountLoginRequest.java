package com.interswitchgroup.discoverpostinjectweb.model.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class AccountLoginRequest {

    private Map<String, String> grantType = new HashMap<>();
    private String username;
    private String password;

    public AccountLoginRequest(String grantType, String username, String password) {
        this.setGrantType("grant_type", grantType);
        this.setUsername(username);
        this.setPassword(password);
    }

    @JsonAnyGetter
    public Map<String, String> getGrantType() {
        return grantType;
    }

    @JsonAnySetter
    public void setGrantType(String key, String grantType) {
        this.grantType.put(key, grantType);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
