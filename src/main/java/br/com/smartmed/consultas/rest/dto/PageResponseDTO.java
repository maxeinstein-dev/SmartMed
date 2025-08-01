package br.com.smartmed.consultas.rest.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DTO genérico para encapsular respostas paginadas.
 *
 * @param <T> O tipo de objeto contido na lista de conteúdo.
 */
@Data
public class PageResponseDTO<T> {
    private List<T> conteudo;
    private int totalPaginas;
    private int paginaAtual;

    public PageResponseDTO(Page<T> page) {
        this.conteudo = page.getContent();
        this.totalPaginas = page.getTotalPages();
        this.paginaAtual = page.getNumber();
    }
}
