package com.project.oauthpro.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProviderService {
    public String getLoginUrl();
    public String getAccessToken(String authorizationCode,String state) throws JsonProcessingException;
}
