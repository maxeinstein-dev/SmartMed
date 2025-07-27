package br.com.smartmed.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "formaPagamento")
public class FormaPagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao", length = 64, nullable = false)
    @NotNull(message = "A descrição não pode ser nula.")
    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @OneToMany(mappedBy = "formaPagamento")
    private List<ConsultaModel> consultas;
}
