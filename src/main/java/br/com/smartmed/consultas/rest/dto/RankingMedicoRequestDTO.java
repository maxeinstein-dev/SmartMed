package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RankingMedicoRequestDTO extends PageableRequestDTO {
    @NotNull(message = "O mês é obrigatório.")
    @Min(value = 1, message = "O mês deve ser um valor entre 1 e 12.")
    @Max(value = 12, message = "O mês deve ser um valor entre 1 e 12.")
    private Integer mes;

    @NotNull(message = "O ano é obrigatório.")
    private Integer ano;
}