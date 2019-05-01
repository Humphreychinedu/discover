package com.interswitchgroup.discoverpostinjectweb.util;

import com.interswitchgroup.discoverpostinjectweb.model.UserAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class AuthenticationUtil {

    public static UserAccount getUser() {
        return (UserAccount) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }

    public static String getUserRole() {
        return getUser().getRole();
    }

    public static String getUserAccessToken() {
        return ((OAuth2AuthenticationDetails) SecurityContextHolder
                .getContext().getAuthentication().getDetails()).getTokenValue();
    }

}
