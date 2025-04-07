package com.renata.api.controller;

import com.renata.api.module.ContactRequest;
import com.renata.api.service.HubSpotContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubspot/contact")
public class HubSpotContactController {

    private HubSpotContactService hubSpotService;

    @PostMapping("/create")
    @Operation(
        summary = "Criação de contato no HubSpot",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Dados do contato a ser criado",
            content = @Content(schema = @Schema(implementation = ContactRequest.class))
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public Mono<Map<String, Object>> createContact(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid ContactRequest contactRequest) {

        return hubSpotService.createContact(authorization, contactRequest);
    }

    @Operation(
        summary = "Recebe Webhook do HubSpot",
        description = "Esse endpoint é chamado pelo HubSpot quando ocorre um evento relevante, como criação ou atualização de contato.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Webhook recebido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
        }
    )
    @PostMapping("/webhook")
    public ResponseEntity<String> receiveContactWebhook(@RequestBody Map<String, Object> payload) {
        hubSpotService.processWebhook(payload);
        return ResponseEntity.ok("Webhook recebido com sucesso!");
    }

}
