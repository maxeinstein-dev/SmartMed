package br.com.smartmed.consultas.rest.dto;

import br.com.smartmed.consultas.model.EspecialidadeModel;
import br.com.smartmed.consultas.model.MedicoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicoResponseDTO {
    private Integer id;
    private String nome;
    private String crm;
    private String telefone;
    private String email;
    private Double valorConsultaReferencia;
    private EspecialidadeDTO especialidade;

    public MedicoResponseDTO(MedicoModel medico) {
        this.id = medico.getId();
        this.nome = medico.getNome();
        this.crm = medico.getCrm();
        this.telefone = medico.getTelefone();
        this.email = medico.getEmail();
        this.valorConsultaReferencia = medico.getValorConsultaReferencia();
        if (medico.getEspecialidade() != null) {
            this.especialidade = new EspecialidadeDTO();
            this.especialidade.setId(medico.getEspecialidade().getId());
            this.especialidade.setNome(medico.getEspecialidade().getNome());
            this.especialidade.setDescricao(medico.getEspecialidade().getDescricao());
        }
    }
}
