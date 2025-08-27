package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para o request de cancelamento de consulta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelamentoConsultaDTO {
    @NotNull(message = "O ID da consulta não pode ser nulo.")
    private Integer consultaId;

    @NotNull(message = "O motivo do cancelamento não pode ser nulo.")
    @NotBlank(message = "O motivo do cancelamento é obrigatório.")
    private String motivo;
}
