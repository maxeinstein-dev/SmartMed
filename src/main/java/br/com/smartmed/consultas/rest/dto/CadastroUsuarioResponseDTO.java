package br.com.smartmed.consultas.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroUsuarioResponseDTO {
    private String mensagem;
    private Integer usuarioID;
}
