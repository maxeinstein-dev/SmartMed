package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.FormaPagamentoModel;
import br.com.smartmed.consultas.repository.FormaPagamentoRepository;
import br.com.smartmed.consultas.rest.dto.FormaPagamentoDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lista todas as formas de pagamento.
     */
    @Transactional(readOnly = true)
    public List<FormaPagamentoDTO> obterTodas() {
        List<FormaPagamentoModel> formas = formaPagamentoRepository.findAll();
        return formas.stream()
                .map(forma -> modelMapper.map(forma, FormaPagamentoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca uma forma de pagamento por ID.
     */
    @Transactional(readOnly = true)
    public FormaPagamentoDTO obterPorId(int id) {
        FormaPagamentoModel forma = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + id + " não encontrada."));
        return modelMapper.map(forma, FormaPagamentoDTO.class);
    }

    @Transactional(readOnly = true)
    public FormaPagamentoModel obterFormaPagamentoModelPorId(Integer id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de Pagamento com ID " + id + " não encontrada."));
    }

    /**
     * Busca formas de pagamento pela descrição (parcial, ignorando case).
     */
    @Transactional(readOnly = true)
    public List<FormaPagamentoDTO> buscarPorDescricao(String descricao) {
        List<FormaPagamentoModel> formas = formaPagamentoRepository.findByDescricaoContainingIgnoreCase(descricao);
        if (formas.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma forma de pagamento encontrado com a descricao: " + descricao);
        }
        return formas.stream()
                .map(forma -> modelMapper.map(forma, FormaPagamentoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Cadastra uma nova forma de pagamento.
     */
    @Transactional
    public FormaPagamentoDTO salvar(@Valid FormaPagamentoModel novaForma) {
        try {
            if (formaPagamentoRepository.existsByDescricao(novaForma.getDescricao())) {
                throw new ConstraintException("Já existe uma forma de pagamento com esta descrição cadastrada: " + novaForma.getDescricao());
            }
            return modelMapper.map(formaPagamentoRepository.save(novaForma), FormaPagamentoDTO.class);
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao salvar a nova forma de pagamento ID " + novaForma.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a nova forma de pagamento ID " + novaForma.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a nova forma de pagamento ID " + novaForma.getId() + ". Falha na conexão com o banco de dados.");
        }

    }

    /**
     * Atualiza uma forma de pagamento existente.
     */
    @Transactional
    public FormaPagamentoDTO atualizar(@Valid FormaPagamentoModel formaExistente) {
        try {
            if (!formaPagamentoRepository.existsById(formaExistente.getId())) {
                throw new ObjectNotFoundException("A forma de pagamento com ID " + formaExistente.getId() + " não existe na base de dados!");
            }

            return modelMapper.map(formaPagamentoRepository.save(formaExistente), FormaPagamentoDTO.class);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a forma de pagamento ID " + formaExistente.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao atualizar a forma de pagamento ID " + formaExistente.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a forma de pagamento ID " + formaExistente.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a forma de pagamento ID " + formaExistente.getId() + ". Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Deleta uma forma de pagamento pelo ID.
     */
    @Transactional
    public void deletar(FormaPagamentoModel formaExistente) {
        try {
            if (!formaPagamentoRepository.existsById(formaExistente.getId())) {
                throw new ObjectNotFoundException("Forma de pagamento não encontrada no banco de dados!");
            }
            formaPagamentoRepository.delete(formaExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a forma de pagamento ID " + formaExistente.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao deletar a forma de pagamento ID " + formaExistente.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a forma de pagamento ID " + formaExistente.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar a forma de pagamento ID " + formaExistente.getId() + ". Falha na conexão com o banco de dados.");
        }
    }
}
