package br.com.smartmed.consultas.rest.dto;

import br.com.smartmed.consultas.model.ConsultaStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CadastrarConsultaResponseDTO {
    private String mensagem;
    private ConsultaStatus status;
    private DadosConsultaDTO dadosConsulta;

    @Data
    public static class DadosConsultaDTO {
        private Integer id;
        private LocalDateTime dataHora;
        private MedicoDTO medico;
        private PacienteDTO paciente;
        private BigDecimal valor;
    }
}