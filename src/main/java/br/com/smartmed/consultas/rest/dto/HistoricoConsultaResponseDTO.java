package br.com.smartmed.consultas.rest.dto;

import br.com.smartmed.consultas.model.ConsultaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoConsultaResponseDTO {
    private LocalDateTime dataHora;
    private String medico;
    private String especialidade;
    private BigDecimal valor;
    private ConsultaStatus status;
    private String observacoes;
}
