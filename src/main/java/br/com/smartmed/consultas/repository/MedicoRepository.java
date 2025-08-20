package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.EspecialidadeModel;
import br.com.smartmed.consultas.model.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoModel, Integer> {

    /**
     * Busca médicos pelo nome (parcial e ignorando case).
     */
    List<MedicoModel> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca médico pelo CRM (exato e ignorando case).
     */
    MedicoModel findByCrmIgnoreCase(String crm);

    /**
     * Verifica se já existe um médico com determinado CRM.
     */
    boolean existsByCrm(String crm);

    /**
     * Busca médicos por especialidade.
     * @param especialidade Especialidade a ser filtrada.
     * @return Lista de médicos da especialidade.
     */
    List<MedicoModel> findByEspecialidade(EspecialidadeModel especialidade);

    Optional<MedicoModel> findByUsuarioId(Integer usuarioId);
}
