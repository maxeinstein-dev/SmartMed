package br.com.smartmed.consultas.rest.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * DTO para a requisição de filtro de recepcionistas.
 * Inclui os campos de filtro e informações de paginação.
 */
@Data
public class RecepcionistaFilterRequestDTO {
    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataFim;

    private int pagina = 0; // Valor padrão para a página
    private int tamanhoPagina = 10; // Valor padrão para o tamanho da página
}

