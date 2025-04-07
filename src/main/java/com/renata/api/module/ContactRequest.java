package com.renata.api.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Requisição para criação de um novo contato no HubSpot")
public class ContactRequest {

    @NotBlank
    @Email(message = "O e-mail deve ter um formato válido")
    @JsonProperty("email")
    @Schema(description = "Endereço de e-mail do contato", example = "example@hubspot.com", required = true)
    private String email;

    @JsonProperty("firstname")
    @Schema(description = "Primeiro nome do contato", example = "Jane")
    private String firstname;

    @JsonProperty("lastname")
    @Schema(description = "Último nome do contato", example = "Doe")
    private String lastname;

    @JsonProperty("phone")
    @Schema(description = "Número de telefone do contato", example = "(555) 555-5555")
    private String phone;

    @JsonProperty("company")
    @Schema(description = "Empresa associada ao contato", example = "HubSpot")
    private String company;

    @JsonProperty("website")
    @Schema(description = "Website do contato ou da empresa", example = "hubspot.com")
    private String website;

    @JsonProperty("lifecyclestage")
    @Schema(description = "Estágio do ciclo de vida do contato", example = "marketingqualifiedlead")
    private String lifecyclestage;
}
