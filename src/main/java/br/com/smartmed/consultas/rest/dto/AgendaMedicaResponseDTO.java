package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaMedicaResponseDTO {
    private String medico;
    private LocalDate data;
    private List<String> horariosOcupados;
    private List<String> horariosDisponiveis;
}
