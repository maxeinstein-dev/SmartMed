package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.EspecialidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadeRepository extends JpaRepository<EspecialidadeModel, Integer> {

    /**
     * Busca especialidades por nome (parcial, ignorando case).
     */
    List<EspecialidadeModel> findByNomeContainingIgnoreCase(String nome);

    /**
     * Verifica se já existe uma especialidade com determinado nome.
     */
    boolean existsByNome(String nome);
}
