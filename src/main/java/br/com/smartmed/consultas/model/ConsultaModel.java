package br.com.smartmed.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consulta")
public class ConsultaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dataHoraConsulta", nullable = false)
    private LocalDateTime dataHoraConsulta;

    @Column(name = "status", length = 16, nullable = false)
    @NotNull(message = "O status não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    private ConsultaStatus status;

    @Column(name = "valor", nullable = false)
    @NotNull(message = "O valor não pode ser nulo.")
    private BigDecimal valor;

    @Column(name = "observacoes", length = 1024, nullable = true)
    private String observacoes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pacienteId", nullable = false)
    @NotNull(message = "O pacienteId não pode ser nulo.")
    private PacienteModel paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicoId", nullable = false)
    @NotNull(message = "O medicoId não pode ser nulo.")
    private MedicoModel medico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formaPagamentoId")
    private FormaPagamentoModel formaPagamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "convenioId")
    private ConvenioModel convenio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recepcionistaId", nullable = false)
    @NotNull(message = "O recepcionistaId não pode ser nulo.")
    private RecepcionistaModel recepcionista;

    @PrePersist
    @PreUpdate
    private void calcularValor() {
        if (medico != null) {
            BigDecimal valorBase = BigDecimal.valueOf(medico.getValorConsultaReferencia());

            // A consulta foi cancelada, o valor é zero.
            if (status == ConsultaStatus.CANCELADA) {
                this.valor = BigDecimal.ZERO;
                return;
            }

            // Aplica desconto do convênio, se houver
            if (convenio != null && convenio.getPorcentagemDesconto() != null) {
                BigDecimal porcentagemDesconto = convenio.getPorcentagemDesconto();
                BigDecimal desconto = BigDecimal.ONE.subtract(porcentagemDesconto);
                this.valor = valorBase.multiply(desconto);
            } else {
                this.valor = valorBase;
            }
        }
    }
}
