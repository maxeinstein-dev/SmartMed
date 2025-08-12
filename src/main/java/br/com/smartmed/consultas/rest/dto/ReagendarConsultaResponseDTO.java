package br.com.smartmed.consultas.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReagendarConsultaResponseDTO {
    private String mensagem;
    private LocalDateTime novaDataHora;
}
