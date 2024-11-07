package com.project.oauthpro.service.naver;


import com.project.oauthpro.service.ProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@Service
public class NaverService implements ProviderService {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String TOKEN_URL;

    @Override
    public String getLoginUrl() {
        String state = UUID.randomUUID().toString();

        return UriComponentsBuilder.fromHttpUrl(authorizationUri)
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .toUriString();
    }

    public String getAccessToken(String authorizationCode, String state) {
        // 토큰 요청 URL 구성
        String url = UriComponentsBuilder.fromHttpUrl(TOKEN_URL)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", authorizationCode)
                .queryParam("state", state)
                .toUriString();

        // RestTemplate을 사용하여 POST 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = response.getBody();

        // 액세스 토큰 추출
        if (body != null && body.containsKey("access_token")) {
            return (String) body.get("access_token");
        }
        return null;
    }
}
