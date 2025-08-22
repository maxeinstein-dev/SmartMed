package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.RecepcionistaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
     *
     * @return Optional contendo a recepcionista ativa, se encontrada.
     */
    Optional<RecepcionistaModel> findFirstByAtivoTrue();

    Optional<RecepcionistaModel> findByEmail(String email);

    Optional<RecepcionistaModel> findByUsuarioId(Integer usuarioId);

    /**
     * Busca recepcionistas com filtros de status e faixa de data de admissão, com paginação.
     * Os parâmetros são opcionais.
     *
     * @param status     Status da recepcionista ("ATIVO" ou "INATIVO"). Pode ser nulo.
     * @param dataInicio Data de início da faixa de admissão. Pode ser nulo.
     * @param dataFim    Data de fim da faixa de admissão. Pode ser nulo.
     * @param pageable   Objeto de paginação.
     * @return Uma página de objetos RecepcionistaModel que correspondem aos critérios.
     */
    @Query("SELECT r FROM RecepcionistaModel r WHERE " +
            "(:status IS NULL OR r.ativo = :status) AND " +
            "(:dataInicio IS NULL OR r.dataAdmissao >= :dataInicio) AND " +
            "(:dataFim IS NULL OR r.dataAdmissao <= :dataFim)")
    Page<RecepcionistaModel> findByStatusAndDataAdmissao(
            @Param("status") Boolean status,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable
    );
}
