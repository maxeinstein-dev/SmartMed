package br.com.smartmed.consultas.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageableRequestDTO {
    private int pagina = 0;
    private int tamanhoPagina = 10;
}
