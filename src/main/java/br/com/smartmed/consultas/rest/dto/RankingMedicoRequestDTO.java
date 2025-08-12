package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingMedicoRequestDTO {

    @NotNull(message = "O mês é obrigatório.")
    @Min(value = 1, message = "O mês deve ser entre 1 e 12.")
    @Max(value = 12, message = "O mês deve ser entre 1 e 12.")
    private Integer mes;

    @NotNull(message = "O ano é obrigatório.")
    @Min(value = 2000, message = "O ano deve ser válido.")
    private Integer ano;

    // Parâmetros de paginação
    private int page = 0;
    private int size = 10;
    private String sort = "quantidadeConsultas,desc"; // Valor padrão para ordenação
}