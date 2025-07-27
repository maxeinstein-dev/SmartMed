package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.RecepcionistaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecepcionistaRepository extends JpaRepository<RecepcionistaModel, Integer> {

    /**
     * Busca recepcionista pelo nome (ignorando case).
     *
     * @param nome Nome da recepcionista.
     * @return Lista de recepcionistas encontrados.
     */
    List<RecepcionistaModel> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca recepcionista pelo CPF exato.
     *
     * @param cpf CPF da recepcionista.
     * @return Recepcionista encontrado (se houver).
     */
    RecepcionistaModel findByCpf(String cpf);

    /**
     * Verifica se existe recepcionista cadastrado com determinado CPF.
     *
     * @param cpf CPF a verificar.
     * @return True se existir, False caso contrário.
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se existe recepcionista cadastrado com determinado nome.
     *
     * @param nome Nome a verificar.
     * @return True se existir, False caso contrário.
     */
    boolean existsByNome(String nome);

    /**
     * Busca a primeira recepcionista ativa.
     * @return Optional contendo a recepcionista ativa, se encontrada.
     */
    Optional<RecepcionistaModel> findFirstByAtivoTrue();
}
