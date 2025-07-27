package br.com.smartmed.consultas.rest.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class MedicoDTO {
    private Integer id;
    private String nome;
    private String crm;
    private String telefone;
    private String email;
    private Double valorConsultaReferencia;
    private boolean ativo;
    private EspecialidadeDTO especialidade;
    private LocalTime horaInicioExpediente;
    private LocalTime horaFimExpediente;
    private Integer duracaoPadraoConsulta;
}
