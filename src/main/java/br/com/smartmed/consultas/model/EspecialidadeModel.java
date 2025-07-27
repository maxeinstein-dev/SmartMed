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
@Table(name = "especialidade")
public class EspecialidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 64, nullable = false, unique = true)
    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(name = "descricao", length = 255, nullable = true)
    @NotBlank(message = "A descrição não pode ser vazia mas pode ser nula.")
    private String descricao;

    @OneToMany(mappedBy = "especialidade")
    @Column(name = "medicos")
    private List<MedicoModel> medicos;
}
