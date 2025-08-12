package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoAutomaticoResponseDTO {
    private Integer id;
    private LocalDateTime dataHoraConsulta;
    private BigDecimal valor;
    private MedicoDTO medico;
    private PacienteDTO paciente;
}
