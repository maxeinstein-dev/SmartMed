package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoConsultaResponseDTO {
    private LocalDateTime dataHora;
    private String medico; // Nome do médico
    private String especialidade; // Nome da especialidade
    private Double valor;
    private String status;
    private String observacoes;
}
