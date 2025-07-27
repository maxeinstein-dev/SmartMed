package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.FormaPagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Integer> {
    /**
     * Busca formas de pagamento pela descrição (parcial e ignorando case).
     */
    List<FormaPagamentoModel> findByDescricaoContainingIgnoreCase(String descricao);

    /**
     * Verifica se já existe uma forma de pagamento com determinada descrição.
     */
    boolean existsByDescricao(String descricao);
}
