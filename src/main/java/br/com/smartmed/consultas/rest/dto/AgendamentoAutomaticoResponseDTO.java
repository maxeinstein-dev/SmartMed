package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoAutomaticoResponseDTO {
    private Long id;
    private LocalDateTime dataHoraConsulta;
    private Double valor;
    private MedicoDTO medico;
    private PacienteDTO paciente;
}
