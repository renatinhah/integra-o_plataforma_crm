package com.renata.api.controller;

import com.renata.api.service.HubSpotOAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubspot")
@Tag(name = "HubSpot API", description = "Endpoints pra integração com o HubSpot - Autenticações")
public class HubSpotOAuthController {

    private HubSpotOAuthService hubSpotService;

    @GetMapping("/auth-url")
    @Operation(
        summary = "Gerar URL de autenticação",
        description = "Gera a URL pra redirecionamento do usuário ao consentimento do HubSpot (OAuth 2.0 Authorization Code Flow)",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "URL gerada com sucesso",
                content = @Content(mediaType = "text/plain")
            )
        }
    )
    public ResponseEntity<String> getAuthorizationCode() {
        String urlAuthorizationCode = hubSpotService.createUrlToGetCode();
        return new ResponseEntity<>(urlAuthorizationCode, HttpStatus.OK);
    }

    @GetMapping("/callback")
    @Operation(
        summary = "Receber código de autorização",
        description = "Endpoint que recebe o código de autorização do HubSpot e troca por um token de acesso",
        parameters = {
            @Parameter(name = "code", description = "Código de autorização enviado pelo HubSpot após o consentimento", required = true)
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Token de acesso retornado com sucesso",
                content = @Content(mediaType = "text/plain")
            )
        }
    )
    public Mono<Map<String, Object>> changeCodeToToken(@RequestParam("code") String code) {
        return hubSpotService.exchangeCodeForToken(code);
    }

}
