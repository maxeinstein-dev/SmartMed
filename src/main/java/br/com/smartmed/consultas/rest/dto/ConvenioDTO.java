package br.com.smartmed.consultas.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConvenioDTO {
    private Integer id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private boolean ativo;
    private BigDecimal porcentagemDesconto;
}
