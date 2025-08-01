package br.com.smartmed.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medico")
public class MedicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 255, nullable = false)
    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(name = "crm", length = 8, nullable = false, unique = true)
    private String crm;

    @Column(name = "telefone", length = 11, nullable = false)
    @Length(min = 11, max = 11, message = "O campo deve ter exatamente 11 números. Ex: 79998628131")
    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Column(name = "email", length = 64, nullable = true)
    @Email(message = "E-mail inválido.")
    private String email;

    @Column(name = "valorConsultaReferencia", nullable = false)
    @NotNull(message = "O valor referência da consulta não pode ser nulo.")
    private Double valorConsultaReferencia;

    @Column(name = "ativo", nullable = false)
    @NotNull(message = "O status não pode ser nulo.")
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "especialidadeId", nullable = false)
    @NotNull(message = "O ID da especialidade não pode ser nulo.")
    private EspecialidadeModel especialidade;

    @Column(name = "horaInicioExpediente", nullable = false)
    private LocalTime horaInicioExpediente = LocalTime.of(8, 0);

    @Column(name = "horaFimExpediente", nullable = false)
    private LocalTime horaFimExpediente = LocalTime.of(18, 0);

    @Column(name = "duracaoPadraoConsulta", nullable = false)
    private Integer duracaoPadraoConsulta = 30; // em minutos
}
