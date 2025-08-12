package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoAutomaticoRequestDTO {
    private Integer pacienteId;
    private Integer especialidadeId;
    private LocalDateTime dataHoraInicial;
    private Integer duracaoConsultaMinutos;
    private Integer convenioId;
    private Integer formaPagamentoId;
    private Integer recepcionistaId;
}
