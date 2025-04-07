package com.renata.api.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
public class HubSpotConfig {

    @Value("${spring.security.oauth2.client.registration.hubspot.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.hubspot.authorization-uri}")
    private String authorizationUrl;

    @Value("${spring.security.oauth2.client.registration.hubspot.redirect-uri}")
    private String redirectUrl;

    @Value("${spring.security.oauth2.client.registration.hubspot.scope}")
    private String scopes;

    @Value("${spring.security.oauth2.client.provider.hubspot.token-uri}")
    private String tokenUrl;

    @Value("${hubspot.api.url}")
    private String urlCreateContacts;
}

