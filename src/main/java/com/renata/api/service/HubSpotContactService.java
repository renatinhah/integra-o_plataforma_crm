package com.renata.api.service;

import com.renata.api.config.HubSpotConfig;
import com.renata.api.module.ContactRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubSpotContactService {

    private final HubSpotConfig hubSpotConfig;
    private final WebClient webClient;

    public Mono<Map<String, Object>> createContact(String authorization, ContactRequest contactRequest) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", contactRequest);

        return webClient.post()
            .uri(hubSpotConfig.getUrlCreateContacts())
            .header("Authorization", authorization)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .doOnError(error -> log.error("Erro ao criar contato no HubSpot: {}", error.getMessage(), error))
            .onErrorResume(error -> {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Erro ao criar contato no HubSpot");
                errorResponse.put("details", error.getMessage());
                return Mono.just(errorResponse);
            });
    }

    public void processWebhook(Map<String, Object> payload) {
        try {
            String email = (String) payload.get("email");
            String nome = (String) payload.get("name");
            LocalDateTime dataRecebimento = LocalDateTime.now();

            log.info("Webhook recebido: Nome: {}, Email: {}, Data: {}", nome, email, dataRecebimento);
        } catch (Exception e) {
            log.error("Erro ao processar webhook: {}", e.getMessage(), e);
        }
    }
}
