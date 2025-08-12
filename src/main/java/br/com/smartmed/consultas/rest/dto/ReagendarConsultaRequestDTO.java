package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReagendarConsultaRequestDTO {
    @NotNull
    private Integer consultaId;
    @NotNull
    @Future
    private LocalDateTime novaDataHora;
    @NotBlank
    private String motivo;
}
