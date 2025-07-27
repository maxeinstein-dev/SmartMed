package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoAutomaticoRequestDTO {
    private Integer pacienteID;
    private Integer medicoID;
    private Integer especialidadeID;
    private LocalDateTime dataHoraInicial;
    private Integer duracaoConsultaMinutos;
    private Integer convenioID;
    private Integer formaPagamentoID;
}
