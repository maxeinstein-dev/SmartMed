package br.com.smartmed.consultas.rest.dto;

import br.com.smartmed.consultas.model.ConsultaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoConsultaRequestDTO {
    private Integer pacienteId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataFim;

    private Integer medicoId;
    private ConsultaStatus status;
    private Integer especialidadeId;
}
