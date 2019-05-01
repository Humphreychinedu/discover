package com.interswitchgroup.discoverpostinjectweb.model.account;

import java.util.Map;

public class AccountLoginResponse {
    private Map<String, String> accessToken;

    public Map<String, String> getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Map<String, String> accessToken) {
        this.accessToken = accessToken;
    }
}
