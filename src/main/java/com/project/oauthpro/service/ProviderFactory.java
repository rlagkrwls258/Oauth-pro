package com.project.oauthpro.service;

import com.project.oauthpro.service.kakao.KakaoService;
import com.project.oauthpro.service.naver.NaverService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProviderFactory {
    private final ProviderService naverService;
    private final ProviderService kakaoService;
    private final Map<String, ProviderService> providerServiceMap;

    public ProviderFactory(NaverService naverService, KakaoService kakaoService) {
        this.naverService = naverService;
        this.kakaoService = kakaoService;
        this.providerServiceMap = Map.of(
                "naver", naverService,
                "kakao", kakaoService
        );
    }

    public ProviderService getProviderService(String provider) {
        return providerServiceMap.getOrDefault(provider.toLowerCase(), null);
    }
}