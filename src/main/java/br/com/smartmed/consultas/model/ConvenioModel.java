package br.com.smartmed.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "convenio")
public class ConvenioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 255, nullable = false, unique = true)
    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(name = "cnpj", length = 14, nullable = false, unique = true)
    @CNPJ(message = "CNPJ inválido.")
    private String cnpj;

    @Column(name = "telefone", length = 11, nullable = false)
    @Length(min = 11, max = 11, message = "O campo deve ter exatamente 11 números. Ex: 79998628131")
    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Column(name = "email", length = 64, nullable = false)
    @Email(message = "E-mail inválido.")
    private String email;

    @Column(name = "ativo", nullable = false)
    @NotNull(message = "O status não pode ser nulo.")
    private boolean ativo;

    @OneToMany(mappedBy = "convenio")
    private List<ConsultaModel> consultas;

    @Column(name = "porcentagemDesconto", nullable = false)
    private BigDecimal porcentagemDesconto = new BigDecimal("0.50"); // 50% como valor padrão
}
