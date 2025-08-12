package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CadastrarConsultaRequestDTO {
    @NotNull
    private LocalDateTime dataHora;
    private Integer duracaoMinutos; // Pode ser opcional, usando a duração padrão do médico
    @NotNull
    private Integer pacienteId;
    @NotNull
    private Integer medicoId;
    private Integer convenioId;
    private Integer formaPagamentoId;
    @NotNull
    private Integer recepcionistaId;
}