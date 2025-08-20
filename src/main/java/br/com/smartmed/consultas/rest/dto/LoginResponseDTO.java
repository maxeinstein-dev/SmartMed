package br.com.smartmed.consultas.rest.dto;

import br.com.smartmed.consultas.model.PerfilAcesso;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {
    private String mensagem;
    private String token;
    private PerfilAcesso perfil;

    // Campo genérico para as informações detalhadas
    private Object dadosDoUsuario;
}
