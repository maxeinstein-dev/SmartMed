package br.com.smartmed.consultas.rest.dto;

import br.com.smartmed.consultas.model.RecepcionistaModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionistaResponseDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public RecepcionistaResponseDTO(RecepcionistaModel recepcionista) {
        this.id = recepcionista.getId();
        this.nome = recepcionista.getNome();
        this.cpf = recepcionista.getCpf();
        this.telefone = recepcionista.getTelefone();
        this.email = recepcionista.getEmail();
    }
}
