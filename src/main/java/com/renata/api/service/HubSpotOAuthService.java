package com.renata.api.service;

import com.renata.api.config.HubSpotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubSpotOAuthService {
    private final HubSpotConfig hubSpotConfig;
    private final WebClient webClient;

    public String createUrlToGetCode() {
        return String.format(
            "%s?client_id=%s&redirect_uri=%s&scope=%s",
            hubSpotConfig.getAuthorizationUrl(),
            hubSpotConfig.getClientId(),
            hubSpotConfig.getRedirectUrl(),
            URLEncoder.encode(hubSpotConfig.getScopes(), StandardCharsets.UTF_8)
        );
    }

    public Mono<Map<String, Object>> exchangeCodeForToken(String code) {
        return webClient.post()
            .uri(hubSpotConfig.getTokenUrl())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue("grant_type=authorization_code&client_id=" + hubSpotConfig.getClientId() +
                    "&client_secret=" + hubSpotConfig.getClientSecret() +
                    "&redirect_uri=" + hubSpotConfig.getRedirectUrl() +
                    "&code=" + code
            )
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

}
