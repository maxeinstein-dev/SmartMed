package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.RecepcionistaModel;
import br.com.smartmed.consultas.repository.RecepcionistaRepository;
import br.com.smartmed.consultas.rest.dto.RecepcionistaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Serviço responsável pelas operações relacionadas as recepcionistas.
 */
@Service
public class RecepcionistaService {

    /**
     * Instância do repositório de Recepcionistas, responsável por realizar operações de
     * persistência e consulta diretamente no banco de dados.
     */
    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtém um Recepcionista pelo ID.
     *
     * @param id ID da Recepcionista.
     * @return RecepcionistaDTO representando a Recepcionista encontrada.
     * @throws ObjectNotFoundException Se a Recepcionista não for encontrada.
     */
    @Transactional(readOnly = true)
    public RecepcionistaDTO obterPorId(int id) {
        RecepcionistaModel recepcionista = recepcionistaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Recepcionista com ID " + id + " não encontrada."));
        return modelMapper.map(recepcionista, RecepcionistaDTO.class);
    }

    @Transactional(readOnly = true)
    public RecepcionistaModel obterRecepcionistaAtiva() {
        return recepcionistaRepository.findFirstByAtivoTrue()
                .orElseThrow(() -> new ObjectNotFoundException("Nenhuma recepcionista ativa encontrada para agendamento."));
    }

    /**
     * Obtém a lista de todas as Recepcionistas cadastradas.
     *
     * @return Lista de RecepcionistaDTO representando as Recepcionistas cadastradas.
     */
    @Transactional(readOnly = true)
    public List<RecepcionistaDTO> obterTodos() {
        List<RecepcionistaModel> recepcionistas = recepcionistaRepository.findAll();
        return recepcionistas.stream()
                .map(recepcionista -> modelMapper.map(recepcionista, RecepcionistaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca recepcionistas pelo nome (parcial, ignorando case).
     */
    @Transactional(readOnly = true)
    public List<RecepcionistaDTO> buscarPorNome(String nome) {
        List<RecepcionistaModel> recepcionistas = recepcionistaRepository.findByNomeContainingIgnoreCase(nome);
        if (recepcionistas.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma recepcionista encontrado com o nome: " + nome);
        }
        return recepcionistas.stream()
                .map(recepcionista -> modelMapper.map(recepcionista, RecepcionistaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca uma recepcionista pelo CNPJ (exato).
     */
    @Transactional(readOnly = true)
    public RecepcionistaDTO buscarPorCpf(String cpf) {
        RecepcionistaModel recepcionista = recepcionistaRepository.findByCpf(cpf);
        if (recepcionista == null) {
            throw new ObjectNotFoundException("Nenhuma recepcionista encontrado com o CPF: " + cpf);
        }
        return modelMapper.map(recepcionista, RecepcionistaDTO.class);
    }

    /**
     * Salva uma nova Recepcionista na base de dados.
     *
     * @param novoRecepcionista RecepcionistaModel contendo os dados da nova Recepcionista.
     * @return RecepcionistaDTO representando a Recepcionista salva.
     * @throws ConstraintException    Se o telefone ou e-mail já existirem.
     * @throws DataIntegrityException Se ocorrer violação de integridade.
     * @throws BusinessRuleException  Se houver violação de regra de negócio.
     * @throws SQLException           Se ocorrer falha de conexão com o banco de dados.
     */
    @Transactional
    public RecepcionistaDTO salvar(RecepcionistaModel novoRecepcionista) {
        try {
            //Caso ocorra uma tentativa de salvar uma nova Recepcionista com um cpf já existente.
            if (recepcionistaRepository.existsByCpf(novoRecepcionista.getCpf())) {
                throw new ConstraintException("Já existe uma Recepcionista com esse CPF " + novoRecepcionista.getCpf() + " na base de dados!");
            }
            //Salva a nova Recepcionista na base de dados.
            return modelMapper.map(recepcionistaRepository.save(novoRecepcionista), RecepcionistaDTO.class);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a Recepcionista " + novoRecepcionista.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a Recepcionista " + novoRecepcionista.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a Recepcionista " + novoRecepcionista.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a Recepcionista " + novoRecepcionista.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    /**
     * Atualiza os dados de uma Recepcionista existente.
     *
     * @param RecepcionistaExistente RecepcionistaModel contendo os dados atualizados da recepcionista.
     * @return RecepcionistaDTO representando o Recepcionista atualizado.
     * @throws ConstraintException    Se o telefone ou e-mail não existir.
     * @throws DataIntegrityException Se ocorrer violação de integridade.
     * @throws BusinessRuleException  Se houver violação de regra de negócio.
     * @throws SQLException           Se ocorrer falha de conexão com o banco de dados.
     */
    @Transactional
    public RecepcionistaDTO atualizar(RecepcionistaModel RecepcionistaExistente) {

        try {
            //Caso ocorra uma tentativa de salvar uma Recepcionista que não existe utilizando um CPF.
            if (!recepcionistaRepository.existsByCpf(RecepcionistaExistente.getCpf())) {
                throw new ConstraintException("O Recepcionista com esse CPF " + RecepcionistaExistente.getCpf() + " não existe na base de dados!");
            }

            //Atualiza a Recepcionista na base de dados.
            return modelMapper.map(recepcionistaRepository.save(RecepcionistaExistente), RecepcionistaDTO.class);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a Recepcionista " + RecepcionistaExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a Recepcionista " + RecepcionistaExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a Recepcionista " + RecepcionistaExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a Recepcionista " + RecepcionistaExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a Recepcionista" + RecepcionistaExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }


    /**
     * Deleta uma Recepcionista da base de dados.
     *
     * @param RecepcionistaExistente RecepcionistaModel contendo os dados do Recepcionista a ser deletado.
     * @throws ConstraintException    Se o Recepcionista (id) não existir.
     * @throws DataIntegrityException Se ocorrer violação de integridade.
     * @throws BusinessRuleException  Se houver violação de regra de negócio.
     * @throws SQLException           Se ocorrer falha de conexão com o banco de dados.
     */
    @Transactional
    public void deletar(RecepcionistaModel RecepcionistaExistente) {

        try {
            //Caso ocorra uma tentativa de deletar uma Recepcionista que não existe utilizando o id.
            if (!recepcionistaRepository.existsById(RecepcionistaExistente.getId())) {
                throw new ConstraintException("Recepcionista inexistente na base de dados!");
            }

            //Deletar a Recepcionista na base de dados.
            recepcionistaRepository.delete(RecepcionistaExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a Recepcionista " + RecepcionistaExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a Recepcionista " + RecepcionistaExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a Recepcionista " + RecepcionistaExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a deletar " + RecepcionistaExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a Recepcionista" + RecepcionistaExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }
}
