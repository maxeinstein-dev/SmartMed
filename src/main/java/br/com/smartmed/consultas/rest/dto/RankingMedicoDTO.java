package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankingMedicoDTO {
    private String medico;
    private Long quantidadeConsultas;
}