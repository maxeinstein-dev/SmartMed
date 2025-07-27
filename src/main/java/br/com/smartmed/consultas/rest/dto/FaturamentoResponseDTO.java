package br.com.smartmed.consultas.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FaturamentoResponseDTO {
    private BigDecimal totalGeral;
    private List<FormaPagamentoResumoDTO> porFormaPagamento;
    private List<ConvenioResumoDTO> porConvenio;

    @Data
    public static class FormaPagamentoResumoDTO {
        private String formaPagamento;
        private BigDecimal valor;
    }

    @Data
    public static class ConvenioResumoDTO {
        private String convenio;
        private BigDecimal valor;
        private BigDecimal porcentagemDesconto;
    }
}
