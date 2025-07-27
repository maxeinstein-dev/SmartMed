package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.*;
import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.rest.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações relacionadas às consultas.
 */
@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private ConvenioService convenioService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private RecepcionistaService recepcionistaService;
    @Autowired
    private EspecialidadeService especialidadeService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtém uma consulta pelo ID.
     *
     * @param id ID da consulta.
     * @return ConsultaDTO representando a consulta encontrada.
     * @throws ObjectNotFoundException Se a consulta não for encontrada.
     */
    @Transactional(readOnly = true)
    public ConsultaDTO obterPorId(Long id) {
        ConsultaModel consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Consulta com ID " + id + " não encontrada."));
        return modelMapper.map(consulta, ConsultaDTO.class);

    }

    /**
     * Obtém todas as consultas cadastradas.
     *
     * @return Lista de ConsultaDTO.
     */
    @Transactional(readOnly = true)
    public List<ConsultaDTO> obterTodas() {
        List<ConsultaModel> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(consulta -> modelMapper.map(consulta, ConsultaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtém todas as consultas de um paciente.
     *
     * @param pacienteId ID do paciente.
     * @return Lista de ConsultaDTO.
     */
    @Transactional(readOnly = true)
    public List<ConsultaDTO> obterPorPacienteId(Integer pacienteId) {
        List<ConsultaModel> consultas = consultaRepository.findByPacienteId(pacienteId);
        return consultas.stream()
                .map(consulta -> modelMapper.map(consulta, ConsultaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtém todas as consultas de um médico.
     *
     * @param medicoId ID do médico.
     * @return Lista de ConsultaDTO.
     */
    @Transactional(readOnly = true)
    public List<ConsultaDTO> obterPorMedicoId(Integer medicoId) {
        List<ConsultaModel> consultas = consultaRepository.findByMedicoId(medicoId);
        return consultas.stream()
                .map(consulta -> modelMapper.map(consulta, ConsultaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtém consultas filtrando pelo status.
     *
     * @param status Status da consulta.
     * @return Lista de ConsultaDTO.
     */
    @Transactional(readOnly = true)
    public List<ConsultaDTO> obterPorStatus(String status) {
        List<ConsultaModel> consultas = consultaRepository.findByStatus(status);
        return consultas.stream()
                .map(consulta -> modelMapper.map(consulta, ConsultaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Salva uma nova consulta na base de dados.
     *
     * @param novaConsulta Dados da nova consulta.
     * @return ConsultaDTO representando a consulta salva.
     * @throws ConstraintException    Se houver violação de restrição.
     * @throws DataIntegrityException Se ocorrer violação de integridade.
     * @throws BusinessRuleException  Se houver violação de regra de negócio.
     * @throws SQLException           Se ocorrer falha na conexão.
     */
    @Transactional
    public ConsultaDTO salvar(ConsultaModel novaConsulta) {
        try {
            return modelMapper.map(consultaRepository.save(novaConsulta), ConsultaDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a consulta ID " + novaConsulta.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao salvar a consulta ID " + novaConsulta.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a consulta ID " + novaConsulta.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a consulta ID " + novaConsulta.getId() + ". Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Atualiza uma consulta existente na base de dados.
     *
     * @param consultaExistente Dados da consulta a ser atualizada.
     * @return ConsultaDTO representando a consulta atualizada.
     * @throws ObjectNotFoundException Se a consulta não existir.
     * @throws ConstraintException     Se houver violação de restrição.
     * @throws DataIntegrityException  Se ocorrer violação de integridade.
     * @throws BusinessRuleException   Se houver violação de regra de negócio.
     * @throws SQLException            Se ocorrer falha na conexão.
     */
    @Transactional
    public ConsultaDTO atualizar(ConsultaModel consultaExistente) {
        try {
            if (!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ObjectNotFoundException("Consulta com ID " + consultaExistente.getId() + " não encontrada.");
            }

            return modelMapper.map(consultaRepository.save(consultaExistente), ConsultaDTO.class);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a consulta ID " + consultaExistente.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao atualizar a consulta ID " + consultaExistente.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a consulta ID " + consultaExistente.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a consulta ID " + consultaExistente.getId() + ". Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Deleta uma consulta da base de dados.
     *
     * @param consultaExistente Dados da consulta a ser deletada.
     * @throws ObjectNotFoundException Se a consulta não existir.
     * @throws ConstraintException     Se houver violação de restrição.
     * @throws DataIntegrityException  Se ocorrer violação de integridade.
     * @throws BusinessRuleException   Se houver violação de regra de negócio.
     * @throws SQLException            Se ocorrer falha na conexão.
     */
    @Transactional
    public void deletar(ConsultaModel consultaExistente) {
        try {
            if (!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ObjectNotFoundException("Consulta com ID " + consultaExistente.getId() + " não encontrada.");
            }

            consultaRepository.delete(consultaExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a consulta ID " + consultaExistente.getId() + " devido à violação de integridade.");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao deletar a consulta ID " + consultaExistente.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a consulta ID " + consultaExistente.getId() + ". Violação de regra de negócio.");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar a consulta ID " + consultaExistente.getId() + ". Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Agenda uma consulta automaticamente, encontrando o primeiro horário disponível.
     *
     * @param request DTO com os critérios para agendamento automático.
     * @return DTO com os dados da consulta agendada.
     * @throws ObjectNotFoundException Se paciente, médico, especialidade, convênio ou forma de pagamento não forem encontrados.
     * @throws BusinessRuleException Se não for possível encontrar um horário disponível ou regras de negócio forem violadas.
     */
    @Transactional
    public AgendamentoAutomaticoResponseDTO agendarAutomaticamente(AgendamentoAutomaticoRequestDTO request) {
        // 1. Validar e buscar Paciente usando o serviço de Paciente
        PacienteModel paciente = pacienteService.obterPacienteModelPorId(request.getPacienteID());

        // 2. Buscar Médicos elegíveis (por ID ou Especialidade) usando o serviço de Médico
        List<MedicoModel> medicosElegiveis;
        if (request.getMedicoID() != null) {
            MedicoModel medico = medicoService.buscarMedicoModelPorId(request.getMedicoID());
            medicosElegiveis = List.of(medico);
        } else if (request.getEspecialidadeID() != null) {
            EspecialidadeModel especialidade = especialidadeService.obterEspecialidadeModelPorId(request.getEspecialidadeID());
            medicosElegiveis = medicoService.buscarMedicosModelPorEspecialidade(especialidade);
            if (medicosElegiveis.isEmpty()) {
                throw new ObjectNotFoundException("Nenhum médico encontrado para a especialidade com ID " + request.getEspecialidadeID() + ".");
            }
        } else {
            throw new BusinessRuleException("É necessário informar o ID do médico ou o ID da especialidade para agendamento automático.");
        }

        // 3. Buscar Recepcionista usando o serviço de Recepcionista
        RecepcionistaModel recepcionista = recepcionistaService.obterRecepcionistaAtiva();

        // 4. Buscar Forma de Pagamento usando o serviço de Forma de Pagamento
        FormaPagamentoModel formaPagamento = formaPagamentoService.obterFormaPagamentoModelPorId(request.getFormaPagamentoID());

        // 5. Iterar pelos médicos e buscar o primeiro horário disponível
        for (MedicoModel medico : medicosElegiveis) {
            LocalDateTime dataHoraAtual = request.getDataHoraInicial();
            LocalTime horaInicioExpediente = medico.getHoraInicioExpediente();
            LocalTime horaFimExpediente = medico.getHoraFimExpediente();
            // Usar a duração da requisição se fornecida, senão a duração padrão do médico
            Integer duracaoConsulta = Optional.ofNullable(request.getDuracaoConsultaMinutos())
                    .orElse(medico.getDuracaoPadraoConsulta());

            // Limite de busca: 3 meses a partir da dataHoraInicial
            LocalDateTime limiteBusca = request.getDataHoraInicial().plusMonths(3);

            // Loop para encontrar um horário disponível
            while (dataHoraAtual.isBefore(limiteBusca)) {
                // Pular fins de semana (sábado e domingo)
                if (dataHoraAtual.getDayOfWeek() == DayOfWeek.SATURDAY || dataHoraAtual.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    dataHoraAtual = dataHoraAtual.toLocalDate().plusDays(1).atTime(horaInicioExpediente);
                    continue;
                }

                // Ajustar a hora para o início do expediente do médico se o dia mudou ou se a hora atual é antes do expediente
                if (dataHoraAtual.toLocalTime().isBefore(horaInicioExpediente)) {
                    dataHoraAtual = dataHoraAtual.withHour(horaInicioExpediente.getHour()).withMinute(horaInicioExpediente.getMinute()).withSecond(0).withNano(0);
                }

                // Verificar se o slot proposto termina dentro do expediente do médico
                LocalDateTime fimDoSlot = dataHoraAtual.plusMinutes(duracaoConsulta);
                // Apenas verifica se o fim do slot é estritamente APÓS o fim do expediente
                if (fimDoSlot.toLocalTime().isAfter(horaFimExpediente)) {
                    dataHoraAtual = dataHoraAtual.toLocalDate().plusDays(1).atTime(horaInicioExpediente);
                    continue;
                }

                // Buscar consultas que potencialmente se sobrepõem no período
                List<ConsultaModel> consultasPotenciaisConflito = consultaRepository.findConsultasByMedicoAndPeriod(
                        medico.getId(),
                        dataHoraAtual.minusMinutes(medico.getDuracaoPadraoConsulta()),
                        fimDoSlot.plusMinutes(medico.getDuracaoPadraoConsulta())
                );

                // Lógica de sobreposição em Java
                boolean hasConflict = false;
                for (ConsultaModel consultaExistente : consultasPotenciaisConflito) {
                    LocalDateTime inicioConsultaExistente = consultaExistente.getDataHoraConsulta();
                    Integer duracaoExistente = consultaExistente.getMedico() != null ? consultaExistente.getMedico().getDuracaoPadraoConsulta() : 30;
                    LocalDateTime fimConsultaExistente = inicioConsultaExistente.plusMinutes(duracaoExistente);

                    // Condição de sobreposição: (slot_inicio < consulta_fim AND slot_fim > consulta_inicio)
                    if (dataHoraAtual.isBefore(fimConsultaExistente) && fimDoSlot.isAfter(inicioConsultaExistente)) {
                        hasConflict = true;
                        break;
                    }
                }

                if (!hasConflict) {
                    // Horário disponível encontrado!
                    ConsultaModel novaConsulta = new ConsultaModel();
                    novaConsulta.setDataHoraConsulta(dataHoraAtual);
                    novaConsulta.setStatus("AGENDADA");
                    novaConsulta.setPaciente(paciente);
                    novaConsulta.setMedico(medico);
                    novaConsulta.setRecepcionista(recepcionista);
                    novaConsulta.setFormaPagamento(formaPagamento);

                    BigDecimal valorBase = BigDecimal.valueOf(medico.getValorConsultaReferencia());
                    ConvenioModel convenio = null;
                    if (request.getConvenioID() != null) {
                        convenio = convenioService.obterConvenioModelPorId(request.getConvenioID());
                        novaConsulta.setConvenio(convenio);
                        novaConsulta.setValor(valorBase.multiply(BigDecimal.valueOf(0.50)).doubleValue());
                    } else {
                        novaConsulta.setValor(valorBase.doubleValue());
                    }

                    ConsultaModel consultaAgendada = consultaRepository.save(novaConsulta);

                    AgendamentoAutomaticoResponseDTO responseDTO = new AgendamentoAutomaticoResponseDTO();
                    responseDTO.setId(consultaAgendada.getId());
                    responseDTO.setDataHoraConsulta(consultaAgendada.getDataHoraConsulta());
                    responseDTO.setValor(consultaAgendada.getValor());
                    responseDTO.setMedico(modelMapper.map(consultaAgendada.getMedico(), MedicoDTO.class));
                    responseDTO.setPaciente(modelMapper.map(consultaAgendada.getPaciente(), PacienteDTO.class));
                    return responseDTO;
                }

                dataHoraAtual = dataHoraAtual.plusMinutes(duracaoConsulta);
            }
        }

        throw new BusinessRuleException("Não foi possível encontrar um horário disponível para agendamento com os critérios informados dentro do período de busca.");
    }

    /**
     * Retorna o histórico detalhado de consultas de um paciente, com filtros opcionais.
     *
     * @param request DTO contendo os filtros para a consulta do histórico.
     * @return Lista de HistoricoConsultaResponseDTO.
     * @throws ObjectNotFoundException Se o paciente não for encontrado.
     * @throws BusinessRuleException Se o paciente estiver inativo.
     */
    @Transactional(readOnly = true)
    public List<HistoricoConsultaResponseDTO> obterHistoricoConsultas(HistoricoConsultaRequestDTO request) {
        PacienteModel paciente = pacienteService.obterPacienteModelPorIdEAtivo(request.getPacienteID());

        LocalDateTime dataInicio = null;
        if (request.getDataInicio() != null) {
            dataInicio = request.getDataInicio().atStartOfDay();
        }

        LocalDateTime dataFim = null;
        if (request.getDataFim() != null) {
            dataFim = request.getDataFim().atTime(LocalTime.MAX);
        }

        List<ConsultaModel> consultas = consultaRepository.findHistoricoConsultas(
                request.getPacienteID(),
                dataInicio,
                dataFim,
                request.getMedicoID(),
                request.getStatus(),
                request.getEspecialidadeID()
        );

        return consultas.stream().map(consulta -> {
            HistoricoConsultaResponseDTO dto = new HistoricoConsultaResponseDTO();
            dto.setDataHora(consulta.getDataHoraConsulta());
            dto.setMedico(consulta.getMedico() != null ? consulta.getMedico().getNome() : "N/A");
            dto.setEspecialidade(consulta.getMedico() != null && consulta.getMedico().getEspecialidade() != null ?
                    consulta.getMedico().getEspecialidade().getNome() : "N/A");
            dto.setValor(consulta.getValor());
            dto.setStatus(consulta.getStatus());
            dto.setObservacoes(consulta.getObservacoes());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Retorna a agenda de um médico para uma data específica, com horários disponíveis e ocupados.
     *
     * @param request DTO com o ID do médico e a data.
     * @return AgendaMedicoResponseDTO com os horários.
     * @throws ObjectNotFoundException Se o médico não for encontrado.
     * @throws BusinessRuleException Se o médico estiver inativo.
     */
    @Transactional(readOnly = true)
    public AgendaMedicoResponseDTO obterAgendaMedico(AgendaMedicoRequestDTO request) {
        // 1. Verificar se o médico existe e está ativo
        MedicoModel medico = medicoService.buscarMedicoModelPorIdEAtivo(request.getMedicoID());

        // 2. Definir o período de busca para a data informada
        LocalDateTime inicioDoDia = request.getData().atStartOfDay();
        LocalDateTime fimDoDia = request.getData().atTime(LocalTime.MAX);

        // 3. Obter todas as consultas do médico para a data
        List<ConsultaModel> consultasOcupadas = consultaRepository.findConsultasByMedicoAndDate(
                medico.getId(),
                inicioDoDia,
                fimDoDia
        );

        // 4. Gerar todos os slots de tempo possíveis para o dia
        List<LocalTime> todosOsSlots = new ArrayList<>();
        LocalTime slotAtual = medico.getHoraInicioExpediente();
        LocalTime horaFimExpediente = medico.getHoraFimExpediente();
        Integer duracaoPadraoConsulta = medico.getDuracaoPadraoConsulta();

        while (slotAtual.isBefore(horaFimExpediente)) {
            todosOsSlots.add(slotAtual);
            slotAtual = slotAtual.plusMinutes(duracaoPadraoConsulta);
        }

        // 5. Identificar horários ocupados e disponíveis
        List<String> horariosOcupados = new ArrayList<>();
        List<String> horariosDisponiveis = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime agora = LocalDateTime.now();

        for (LocalTime slot : todosOsSlots) {
            LocalDateTime slotInicio = request.getData().atTime(slot);
            LocalDateTime slotFim = slotInicio.plusMinutes(duracaoPadraoConsulta);

            // Não retornar horários anteriores ao momento atual
            if (slotFim.isBefore(agora)) {
                continue;
            }

            boolean ocupado = false;
            for (ConsultaModel consulta : consultasOcupadas) {
                LocalDateTime inicioConsulta = consulta.getDataHoraConsulta();
                // Garante que o médico da consulta existente está carregado para obter a duração
                Integer duracaoExistente = consulta.getMedico() != null ? consulta.getMedico().getDuracaoPadraoConsulta() : 30;
                LocalDateTime fimConsulta = inicioConsulta.plusMinutes(duracaoExistente);

                // Verifica sobreposição: (slot_inicio < consulta_fim AND slot_fim > consulta_inicio)
                if (slotInicio.isBefore(fimConsulta) && slotFim.isAfter(inicioConsulta)) {
                    ocupado = true;
                    break;
                }
            }

            if (ocupado) {
                horariosOcupados.add(slot.format(formatter));
            } else {
                horariosDisponiveis.add(slot.format(formatter));
            }
        }

        // 6. Construir a resposta
        AgendaMedicoResponseDTO responseDTO = new AgendaMedicoResponseDTO();
        responseDTO.setMedico(medico.getNome());
        responseDTO.setData(request.getData());
        responseDTO.setHorariosOcupados(horariosOcupados);
        responseDTO.setHorariosDisponiveis(horariosDisponiveis);

        return responseDTO;
    }
}
