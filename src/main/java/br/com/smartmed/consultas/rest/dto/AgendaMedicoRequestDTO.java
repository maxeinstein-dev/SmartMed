package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaMedicoRequestDTO {
    @NotNull(message = "O ID do médico é obrigatório.")
    private Integer medicoID;

    @NotNull(message = "A data é obrigatória.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;
}
