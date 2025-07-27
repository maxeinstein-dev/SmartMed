package br.com.smartmed.consultas.rest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FaturamentoRequestDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
