package com.project.oauthpro.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.oauthpro.service.ProviderFactory;
import com.project.oauthpro.service.ProviderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OAuthController {

    private final ProviderFactory providerFactory;

    OAuthController(ProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
    }

    @GetMapping("/oauth2/{providerName}/login")
    public void loginPage(@PathVariable String providerName, HttpServletResponse response) throws IOException {
        ProviderService providerService = providerFactory.getProviderService(providerName);

        response.sendRedirect(providerService.getLoginUrl());
    }

    @GetMapping("/oauth2/{providerName}/code")
    public String accessTokenInfo(@RequestParam("code") String code, @RequestParam("state") String state, @PathVariable String providerName) throws JsonProcessingException {

        ProviderService providerService = providerFactory.getProviderService(providerName);

        return providerService.getAccessToken(code, state);
    }
}
