package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConvenioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConvenioRepository extends JpaRepository<ConvenioModel, Integer> {

    /**
     * Busca convênios pelo nome (ignorando case).
     *
     * @param nome Nome do convênio.
     * @return Lista de convênios encontrados.
     */
    List<ConvenioModel> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca convênio pelo CNPJ exato.
     *
     * @param cnpj CNPJ do convênio.
     * @return Convenio encontrado (se houver).
     */
    ConvenioModel findByCnpj(String cnpj);

    /**
     * Verifica se existe convênio cadastrado com determinado CNPJ.
     *
     * @param cnpj CNPJ a verificar.
     * @return True se existir, False caso contrário.
     */
    boolean existsByCnpj(String cnpj);

    /**
     * Verifica se existe convênio cadastrado com determinado nome.
     *
     * @param nome Nome a verificar.
     * @return True se existir, False caso contrário.
     */
    boolean existsByNome(String nome);
}
