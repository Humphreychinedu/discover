package com.interswitchgroup.discoverpostinjectweb.service;

import com.interswitchgroup.discoverpostinjectweb.config.PassportProperties;
import com.interswitchgroup.discoverpostinjectweb.model.account.AccountLoginRequest;
import com.interswitchgroup.discoverpostinjectweb.model.account.AccountLoginResponse;
import com.interswitchgroup.discoverpostinjectweb.passport.PassportResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class PassportService {

//    private final PassportProperties passportProperties;
//    private final OAuth2RestTemplate passportOAuth2RestTemplate;
//    private final RestTemplate restTemplate;
//    private final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//
//    private static final String AUTHORIZATION = "Authorization";
//
//    @Autowired
//    public PassportService(PassportProperties passportProperties, OAuth2RestTemplate passportClientOAuth2RestTemplate) {
//        this.passportProperties = passportProperties;
//        this.passportOAuth2RestTemplate = passportClientOAuth2RestTemplate;
//        this.restTemplate = restTemplateBuilder.errorHandler(new PassportResponseErrorHandler()).build();
//    }
//
//    private String getBasicAuthorization() {
//        String credentials = passportProperties.getClientId() + ":" + passportProperties.getClientSecret();
//        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes());
//        String encodedString = new String(encodedBytes);
//        return "Basic " + encodedString;
//    }

}
