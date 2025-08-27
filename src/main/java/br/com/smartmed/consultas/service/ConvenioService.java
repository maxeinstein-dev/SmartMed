package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.ConvenioModel;
import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.repository.ConvenioRepository;
import br.com.smartmed.consultas.rest.dto.ConvenioDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ConsultaRepository consultaRepository;

    /**
     * Lista todos os convênios.
     */
    @Transactional(readOnly = true)
    public List<ConvenioDTO> obterTodos() {
        List<ConvenioModel> convenios = convenioRepository.findAll();
        return convenios.stream()
                .map(convenio -> modelMapper.map(convenio, ConvenioDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca um convênio por ID.
     */
    @Transactional(readOnly = true)
    public ConvenioDTO obterPorId(int id) {
        ConvenioModel convenio = convenioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Convenio com ID " + id + " não encontrado."));
        return modelMapper.map(convenio, ConvenioDTO.class);
    }

    @Transactional(readOnly = true)
    public ConvenioModel obterConvenioModelPorId(Integer id) {
        return convenioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Convênio com ID " + id + " não encontrado."));
    }

    /**
     * Busca convênios pelo nome (parcial, ignorando case).
     */
    @Transactional(readOnly = true)
    public List<ConvenioDTO> buscarPorNome(String nome) {
        List<ConvenioModel> convenios = convenioRepository.findByNomeContainingIgnoreCase(nome);
        if (convenios.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum convênio encontrado com o nome: " + nome);
        }
        return convenios.stream()
                .map(convenio -> modelMapper.map(convenio, ConvenioDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca um convênio pelo CNPJ (exato).
     */
    @Transactional(readOnly = true)
    public ConvenioDTO buscarPorCnpj(String cnpj) {
        ConvenioModel convenio = convenioRepository.findByCnpj(cnpj);
        if (convenio == null) {
            throw new ObjectNotFoundException("Nenhum convênio encontrado com o CNPJ: " + cnpj);
        }
        return modelMapper.map(convenio, ConvenioDTO.class);
    }

    /**
     * Cadastra um novo convênio.
     */
    @Transactional
    public ConvenioDTO salvar(@Valid ConvenioModel novoConvenio) {
        try {
            if (convenioRepository.existsByCnpj(novoConvenio.getCnpj())) {
                throw new ConstraintException("Já existe um convênio com esse CNPJ " + novoConvenio.getCnpj() + " na base de dados!");
            }
            if (convenioRepository.existsByNome(novoConvenio.getNome())) {
                throw new ConstraintException("Já existe um convênio com esse nome " + novoConvenio.getNome() + " na base de dados!");
            }
            return modelMapper.map(convenioRepository.save(novoConvenio), ConvenioDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o convênio ID " + novoConvenio.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao salvar o convênio ID " + novoConvenio.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o convênio ID " + novoConvenio.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o convênio ID " + novoConvenio.getId() + ". Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Atualiza um convênio existente.
     *
     * @param convenioExistente convenioModel contendo os dados atualizados do cliente.
     * @return convenioDTO representando o convenio atualizado.
     * @throws ConstraintException    Se o telefone ou e-mail não existir.
     * @throws DataIntegrityException Se ocorrer violação de integridade.
     * @throws BusinessRuleException  Se houver violação de regra de negócio.
     * @throws SQLException           Se ocorrer falha de conexão com o banco de dados.
     */
    @Transactional
    public ConvenioDTO atualizar(@Valid ConvenioModel convenioExistente) {

        try {
            //Caso ocorra uma tentativa de salvar um convenio que não existe utilizando um cnpj.
            if (!convenioRepository.existsByCnpj(convenioExistente.getCnpj())) {
                throw new ConstraintException("O convenio com esse CNPJ " + convenioExistente.getCnpj() + " não existe na base de dados!");
            }

            return modelMapper.map(convenioRepository.save(convenioExistente), ConvenioDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o convenio " + convenioExistente.getNome() + " !");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o convenio " + convenioExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o convenio " + convenioExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o convenio " + convenioExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o convenio" + convenioExistente.getNome() + ". Não encontrado no banco de dados!");
        }

    }

    /**
     * Deleta um convênio pelo ID.
     */
    @Transactional
    public void deletar(Integer id) {
        try {
            // Verifica se o convênio existe antes de tentar deletar
            if (!convenioRepository.existsById(id)) {
                throw new ObjectNotFoundException("Convênio com ID " + id + " não encontrado no banco de dados!");
            }

            boolean hasAssociatedConsultas = consultaRepository.existsByConvenioId(id);
            if (hasAssociatedConsultas) {
                throw new BusinessRuleException("Não é possível deletar este convênio, pois há consultas associadas.");
            }

            convenioRepository.deleteById(id);

        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o convênio com ID " + id + ". Violação de regra de negócio!");
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao deletar o convênio com ID " + id + ".");
        }
    }
}
