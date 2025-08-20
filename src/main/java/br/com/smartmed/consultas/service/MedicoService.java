package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.EspecialidadeModel;
import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.repository.MedicoRepository;
import br.com.smartmed.consultas.rest.dto.MedicoDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lista todos os médicos.
     */
    @Transactional(readOnly = true)
    public List<MedicoDTO> obterTodos() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        return medicos.stream()
                .map(medico -> modelMapper.map(medico, MedicoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca um médico por ID.
     */
    @Transactional(readOnly = true)
    public MedicoModel obterMedicoPorId(Integer id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Médico com ID " + id + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public MedicoModel obterMedicoPorUsuarioId(Integer usuarioId) {
        return medicoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ObjectNotFoundException("Dados de médico não encontrados para o usuário com ID " + usuarioId));
    }

    @Transactional(readOnly = true)
    public MedicoModel obterMedicoModelPorId(Integer id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Médico com ID " + id + " não encontrado."));
    }

    /**
     * Busca o MedicoModel por ID, verificando se está ativo.
     *
     * @param id ID do médico.
     * @return MedicoModel representando o médico encontrado e ativo.
     * @throws ObjectNotFoundException Se o médico não for encontrado.
     * @throws BusinessRuleException   Se o médico estiver inativo.
     */
    @Transactional(readOnly = true)
    public MedicoModel buscarMedicoModelPorIdEAtivo(Integer id) {
        MedicoModel medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Médico com ID " + id + " não encontrado."));
        if (!medico.isAtivo()) {
            throw new BusinessRuleException("Médico com ID " + id + " está inativo e não pode ter a agenda consultada.");
        }
        return medico;
    }

    /**
     * Busca médicos pelo nome (parcial, ignorando case).
     */
    @Transactional(readOnly = true)
    public List<MedicoDTO> buscarPorNome(String nome) {
        List<MedicoModel> medicos = medicoRepository.findByNomeContainingIgnoreCase(nome);
        if (medicos.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum médico encontrado com o nome: " + nome);
        }
        return medicos.stream()
                .map(medico -> modelMapper.map(medico, MedicoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca médico pelo CRM (exato, ignorando case).
     */
    @Transactional(readOnly = true)
    public MedicoDTO buscarPorCrm(String crm) {
        MedicoModel medico = medicoRepository.findByCrmIgnoreCase(crm);
        if (medico == null) {
            throw new ObjectNotFoundException("Médico com CRM " + crm + " não encontrado.");
        }
        return modelMapper.map(medico, MedicoDTO.class);
    }

    @Transactional(readOnly = true)
    public List<MedicoModel> buscarMedicosModelPorEspecialidade(EspecialidadeModel especialidade) {
        return medicoRepository.findByEspecialidade(especialidade);
    }

    /**
     * Cadastra um novo médico.
     */
    @Transactional
    public MedicoDTO salvar(@Valid MedicoModel novoMedico) {
        try {
            if (medicoRepository.existsByCrm(novoMedico.getCrm())) {
                throw new ConstraintException("Já existe um médico com esse CRM " + novoMedico.getCrm() + " na base de dados!");
            }
            return modelMapper.map(medicoRepository.save(novoMedico), MedicoDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o médico ID " + novoMedico.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao salvar o médico ID " + novoMedico.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o médico ID " + novoMedico.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o médico ID " + novoMedico.getId() + ". Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Atualiza os dados de um médico existente.
     */
    @Transactional
    public MedicoDTO atualizar(@Valid MedicoModel medicoExistente) {
        try {
            if (!medicoRepository.existsById(medicoExistente.getId())) {
                throw new ObjectNotFoundException("Médico com ID " + medicoExistente.getId() + " não encontrado.");
            }
            return modelMapper.map(medicoRepository.save(medicoExistente), MedicoDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o médico ID " + medicoExistente.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao atualizar o médico ID " + medicoExistente.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o médico ID " + medicoExistente.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o médico ID " + medicoExistente.getId() + ". Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Deleta um médico.
     */
    @Transactional
    public void deletar(MedicoModel medicoExistente) {
        try {
            if (!medicoRepository.existsById(medicoExistente.getId())) {
                throw new ObjectNotFoundException("Médico não encontrado no banco de dados.");
            }
            medicoRepository.delete(medicoExistente);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o médico ID " + medicoExistente.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao deletar o médico ID " + medicoExistente.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o médico ID " + medicoExistente.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar o médico ID " + medicoExistente.getId() + ". Falha na conexão com o banco de dados.");
        }
    }
}
