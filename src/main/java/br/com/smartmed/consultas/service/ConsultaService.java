package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.*;
import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.rest.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public ConsultaDTO obterPorId(Integer id) {
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
     * @throws BusinessRuleException   Se não for possível encontrar um horário disponível ou regras de negócio forem violadas.
     */
    @Transactional
    public AgendamentoAutomaticoResponseDTO agendarAutomaticamente(AgendamentoAutomaticoRequestDTO request) {
        // 1. Validar e buscar entidades principais
        PacienteModel paciente = pacienteService.obterPacienteModelPorId(request.getPacienteId());
        RecepcionistaModel recepcionista = recepcionistaService.obterRecepcionistaPorId(request.getRecepcionistaId());
        FormaPagamentoModel formaPagamento = formaPagamentoService.obterFormaPagamentoModelPorId(request.getFormaPagamentoId());

        // 2. Lógica de busca e ordenação de médicos
        List<MedicoModel> medicosElegiveis;
        if (request.getEspecialidadeId() != null) {
            EspecialidadeModel especialidade = especialidadeService.obterEspecialidadeModelPorId(request.getEspecialidadeId());
            medicosElegiveis = medicoService.buscarMedicosModelPorEspecialidade(especialidade);

            if (medicosElegiveis.isEmpty()) {
                throw new ObjectNotFoundException("Nenhum médico encontrado para a especialidade com ID " + request.getEspecialidadeId() + ".");
            }

            // Critério de ordenação: menor valor da consulta
            //medicosElegiveis.sort(Comparator.comparingDouble(MedicoModel::getValorConsultaReferencia));

            // Aqui, você pode adicionar a lógica de proximidade como um critério secundário, se desejar
            // medicosElegiveis.sort(Comparator.comparingDouble(MedicoModel::getValorConsultaReferencia)
            //                         .thenComparingDouble(medico -> distanciaService.calcularDistancia(paciente.getEndereco(), medico.getEndereco())));

        } else {
            throw new BusinessRuleException("É necessário informar a especialidade para agendamento automático.");
        }

        // 3. Validação de usuários ativos (Recepcionista e Médicos)
        if (!recepcionista.isAtivo()) {
            throw new BusinessRuleException("Recepcionista inativo. Não é possível agendar consultas.");
        }

        for (MedicoModel medico : medicosElegiveis) {
            if (!medico.isAtivo()) {
                throw new BusinessRuleException("Médico inativo. Não é possível agendar consultas para ele.");
            }
        }

        // 4. Iterar pelos médicos e buscar o primeiro horário disponível
        for (MedicoModel medico : medicosElegiveis) {
            LocalDateTime dataHoraAtual = request.getDataHoraInicial();
            LocalTime horaInicioExpediente = medico.getHoraInicioExpediente();
            LocalTime horaFimExpediente = medico.getHoraFimExpediente();
            Integer duracaoConsulta = Optional.ofNullable(request.getDuracaoConsultaMinutos())
                    .orElse(medico.getDuracaoPadraoConsulta());

            LocalDateTime limiteBusca = request.getDataHoraInicial().plusMonths(3);

            while (dataHoraAtual.isBefore(limiteBusca)) {
                if (dataHoraAtual.getDayOfWeek() == DayOfWeek.SATURDAY || dataHoraAtual.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    dataHoraAtual = dataHoraAtual.toLocalDate().plusDays(1).atTime(horaInicioExpediente);
                    continue;
                }

                if (dataHoraAtual.toLocalTime().isBefore(horaInicioExpediente)) {
                    dataHoraAtual = dataHoraAtual.withHour(horaInicioExpediente.getHour()).withMinute(horaInicioExpediente.getMinute());
                }

                LocalDateTime fimDoSlot = dataHoraAtual.plusMinutes(duracaoConsulta);
                if (fimDoSlot.toLocalTime().isAfter(horaFimExpediente)) {
                    dataHoraAtual = dataHoraAtual.toLocalDate().plusDays(1).atTime(horaInicioExpediente);
                    continue;
                }

                List<ConsultaModel> consultasPotenciaisConflito = consultaRepository.findConsultasByMedicoAndPeriod(
                        medico.getId(),
                        dataHoraAtual.minusMinutes(medico.getDuracaoPadraoConsulta()),
                        fimDoSlot.plusMinutes(medico.getDuracaoPadraoConsulta())
                );

                boolean hasConflict = false;
                for (ConsultaModel consultaExistente : consultasPotenciaisConflito) {
                    LocalDateTime inicioConsultaExistente = consultaExistente.getDataHoraConsulta();
                    Integer duracaoExistente = consultaExistente.getMedico() != null ? consultaExistente.getMedico().getDuracaoPadraoConsulta() : 30;
                    LocalDateTime fimConsultaExistente = inicioConsultaExistente.plusMinutes(duracaoExistente);

                    if (dataHoraAtual.isBefore(fimConsultaExistente) && fimDoSlot.isAfter(inicioConsultaExistente)) {
                        hasConflict = true;
                        break;
                    }
                }

                if (!hasConflict) {
                    // Horário disponível encontrado!
                    // 5. Lógica de criação e agendamento da consulta
                    ConsultaModel novaConsulta = new ConsultaModel();
                    novaConsulta.setDataHoraConsulta(dataHoraAtual);
                    novaConsulta.setStatus(ConsultaStatus.AGENDADA);
                    novaConsulta.setPaciente(paciente);
                    novaConsulta.setMedico(medico);
                    novaConsulta.setRecepcionista(recepcionista);
                    novaConsulta.setFormaPagamento(formaPagamento);

                    BigDecimal valorBase = BigDecimal.valueOf(medico.getValorConsultaReferencia());
                    if (request.getConvenioId() != null) {
                        ConvenioModel convenio = convenioService.obterConvenioModelPorId(request.getConvenioId());
                        novaConsulta.setConvenio(convenio);
                        novaConsulta.setValor(valorBase.multiply(BigDecimal.valueOf(0.50)));
                    } else {
                        novaConsulta.setValor(valorBase);
                    }

                    ConsultaModel consultaAgendada = consultaRepository.save(novaConsulta);

                    // 6. Construir e retornar o DTO de resposta
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


    @Transactional
    public CadastrarConsultaResponseDTO cadastrarConsulta(CadastrarConsultaRequestDTO request) {
        // 1. Validar e buscar os envolvidos na consulta
        PacienteModel paciente = pacienteService.obterPacienteModelPorId(request.getPacienteId());
        RecepcionistaModel recepcionista = recepcionistaService.obterRecepcionistaPorId(request.getRecepcionistaId());
        MedicoModel medico = medicoService.obterMedicoModelPorId(request.getMedicoId());

        // 2. Aplicar as Regras de Negócio (Validação de Acesso)
        if (!recepcionista.isAtivo()) {
            throw new BusinessRuleException("Recepcionista inativo. Não é possível agendar consultas.");
        }

        if (!medico.isAtivo()) {
            throw new BusinessRuleException("Médico inativo. Não é possível agendar consultas para ele.");
        }

        // 3. Obter dados auxiliares
        FormaPagamentoModel formaPagamento = formaPagamentoService.obterFormaPagamentoModelPorId(request.getFormaPagamentoId());
        ConvenioModel convenio = request.getConvenioId() != null ? convenioService.obterConvenioModelPorId(request.getConvenioId()) : null;

        // 4. Lógica de Agendamento: Verificar conflito de horário
        LocalDateTime inicioSlot = request.getDataHora();
        Integer duracao = Optional.ofNullable(request.getDuracaoMinutos()).orElse(medico.getDuracaoPadraoConsulta());
        LocalDateTime fimSlot = inicioSlot.plusMinutes(duracao);

        boolean temConflito = consultaRepository.existsByMedicoAndPeriod(medico.getId(), inicioSlot, fimSlot);

        if (temConflito) {
            throw new BusinessRuleException("Já existe uma consulta agendada para este médico nesse horário.");
        }

        // 5. Criar e salvar a nova consulta
        ConsultaModel novaConsulta = new ConsultaModel();
        novaConsulta.setDataHoraConsulta(inicioSlot);
        novaConsulta.setStatus(ConsultaStatus.AGENDADA);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setMedico(medico);
        novaConsulta.setRecepcionista(recepcionista);
        novaConsulta.setFormaPagamento(formaPagamento);
        novaConsulta.setConvenio(convenio);

        // 6. Calcular valor da consulta
        BigDecimal valorBase = BigDecimal.valueOf(medico.getValorConsultaReferencia());
        if (convenio != null) {
            novaConsulta.setValor(valorBase.multiply(BigDecimal.valueOf(0.50)));
        } else {
            novaConsulta.setValor(valorBase);
        }

        ConsultaModel consultaAgendada = consultaRepository.save(novaConsulta);

        // 7. Construir e retornar o DTO de resposta
        CadastrarConsultaResponseDTO responseDTO = new CadastrarConsultaResponseDTO();
        responseDTO.setMensagem("Consulta agendada com sucesso");
        responseDTO.setStatus(ConsultaStatus.AGENDADA);

        CadastrarConsultaResponseDTO.DadosConsultaDTO dadosConsultaDTO = new CadastrarConsultaResponseDTO.DadosConsultaDTO();
        dadosConsultaDTO.setId(consultaAgendada.getId());
        dadosConsultaDTO.setDataHora(consultaAgendada.getDataHoraConsulta());
        dadosConsultaDTO.setMedico(modelMapper.map(consultaAgendada.getMedico(), MedicoDTO.class));
        dadosConsultaDTO.setPaciente(modelMapper.map(consultaAgendada.getPaciente(), PacienteDTO.class));
        dadosConsultaDTO.setValor(consultaAgendada.getValor());

        responseDTO.setDadosConsulta(dadosConsultaDTO);
        return responseDTO;
    }

    /**
     * Retorna o histórico detalhado de consultas de um paciente, com filtros opcionais.
     *
     * @param request DTO contendo os filtros para a consulta do histórico.
     * @return Lista de HistoricoConsultaResponseDTO.
     * @throws ObjectNotFoundException Se o paciente não for encontrado.
     * @throws BusinessRuleException   Se o paciente estiver inativo.
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

    @Transactional(readOnly = true)
    public Page<RankingMedicoDTO> obterRankingMedicosPorMes(int mes, int ano, Pageable pageable) {
        return consultaRepository.findRankingMedicosPorMes(mes, ano, pageable);
    }

    @Transactional(readOnly = true)
    public List<ConsultaModel> buscarConsultasRealizadasNoPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            throw new BusinessRuleException("A data de início não pode ser posterior à data de fim.");
        }

        List<ConsultaModel> consultas = consultaRepository.findConsultasRealizadasByPeriodo(dataInicio, dataFim);

        if (consultas.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma consulta realizada encontrada no período especificado.");
        }

        return consultas;
    }

    /**
     * Retorna a agenda de um médico para uma data específica, com horários disponíveis e ocupados.
     *
     * @param request DTO com o ID do médico e a data.
     * @return AgendaMedicoResponseDTO com os horários.
     * @throws ObjectNotFoundException Se o médico não for encontrado.
     * @throws BusinessRuleException   Se o médico estiver inativo.
     */
    @Transactional(readOnly = true)
    public AgendaMedicaResponseDTO obterAgendaMedico(AgendaMedicaRequestDTO request) {
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
        AgendaMedicaResponseDTO responseDTO = new AgendaMedicaResponseDTO();
        responseDTO.setMedico(medico.getNome());
        responseDTO.setData(request.getData());
        responseDTO.setHorariosOcupados(horariosOcupados);
        responseDTO.setHorariosDisponiveis(horariosDisponiveis);

        return responseDTO;
    }

    /**
     * Cancela uma consulta agendada.
     *
     * @param cancelamentoDTO DTO com o ID da consulta e o motivo do cancelamento.
     * @return DTO da consulta cancelada.
     * @throws ObjectNotFoundException Se a consulta não for encontrada.
     * @throws BusinessRuleException   Se a consulta não puder ser cancelada (por status ou data).
     */
    @Transactional
    public ConsultaDTO cancelarConsulta(CancelamentoConsultaDTO cancelamentoDTO) {
        ConsultaModel consulta = consultaRepository.findById(cancelamentoDTO.getConsultaId())
                .orElseThrow(() -> new ObjectNotFoundException("Consulta com ID " + cancelamentoDTO.getConsultaId() + " não encontrada."));

        // Regra de Negócio: Apenas consultas com status AGENDADA podem ser canceladas.
        if (consulta.getStatus() != ConsultaStatus.AGENDADA) {
            throw new BusinessRuleException("A consulta com ID " + consulta.getId() + " não pode ser cancelada, pois seu status não é 'AGENDADA'.");
        }

        if (consulta.getDataHoraConsulta().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("A consulta com ID " + consulta.getId() + " não pode ser cancelada, pois sua data já é passada.");
        }

        // Atualiza o status e as observações
        consulta.setStatus(ConsultaStatus.CANCELADA);
        consulta.setObservacoes(cancelamentoDTO.getMotivo());

        // O valor será automaticamente atualizado para zero pelo método @PreUpdate na entidade ConsultaModel.
        ConsultaModel consultaCancelada = consultaRepository.save(consulta);
        return modelMapper.map(consultaCancelada, ConsultaDTO.class);
    }

    @Transactional
    public ReagendarConsultaResponseDTO reagendarConsulta(ReagendarConsultaRequestDTO request) {
        // 1. Encontrar a consulta original
        ConsultaModel consultaOriginal = consultaRepository.findById(request.getConsultaId())
                .orElseThrow(() -> new ObjectNotFoundException("Consulta com ID " + request.getConsultaId() + " não encontrada."));

        // 2. Validações da consulta original
        if (consultaOriginal.getStatus() != ConsultaStatus.AGENDADA) {
            throw new BusinessRuleException("A consulta com ID " + consultaOriginal.getId() + " não pode ser reagendada, pois não está com o status 'AGENDADA'.");
        }

        if (consultaOriginal.getDataHoraConsulta().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("A consulta com ID " + consultaOriginal.getId() + " já foi realizada e não pode ser reagendada.");
        }

        // 3. Validação do novo horário e antecedência
        if (request.getNovaDataHora().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new BusinessRuleException("O reagendamento só pode ser feito com pelo menos 1h de antecedência.");
        }

        // 4. Verificar se o novo horário está disponível
        MedicoModel medico = consultaOriginal.getMedico();
        Integer duracaoConsulta = medico.getDuracaoPadraoConsulta();
        LocalDateTime inicioNovoSlot = request.getNovaDataHora();
        LocalDateTime fimNovoSlot = inicioNovoSlot.plusMinutes(duracaoConsulta);

        List<ConsultaModel> consultasConflito = consultaRepository.findConsultasByMedicoAndPeriod(
                medico.getId(),
                inicioNovoSlot,
                fimNovoSlot
        );

        boolean temConflito = consultasConflito.stream()
                .anyMatch(consultaExistente -> {
                    if (consultaExistente.getId().equals(consultaOriginal.getId())) {
                        return false;
                    }
                    LocalDateTime inicioExistente = consultaExistente.getDataHoraConsulta();
                    LocalDateTime fimExistente = inicioExistente.plusMinutes(consultaExistente.getMedico().getDuracaoPadraoConsulta());
                    return inicioNovoSlot.isBefore(fimExistente) && fimNovoSlot.isAfter(inicioExistente);
                });

        if (temConflito) {
            throw new BusinessRuleException("O novo horário " + inicioNovoSlot.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " já está ocupado para o médico " + medico.getNome() + ".");
        }

        // 5. Cancelar a consulta original
        // Este é o método que você já tem, ajustado para receber o DTO
        CancelamentoConsultaDTO cancelamentoDTO = new CancelamentoConsultaDTO();
        cancelamentoDTO.setConsultaId(consultaOriginal.getId());
        cancelamentoDTO.setMotivo("Reagendamento para o novo horário: " + request.getNovaDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " - Motivo: " + request.getMotivo());
        cancelarConsulta(cancelamentoDTO);

        // 6. Criar e agendar a nova consulta (toda a lógica aqui dentro)
        ConsultaModel novaConsulta = new ConsultaModel();
        novaConsulta.setDataHoraConsulta(request.getNovaDataHora());
        novaConsulta.setStatus(ConsultaStatus.AGENDADA);
        novaConsulta.setObservacoes("Reagendamento da consulta original ID: " + consultaOriginal.getId() + ". Motivo: " + request.getMotivo());
        novaConsulta.setPaciente(consultaOriginal.getPaciente());
        novaConsulta.setMedico(medico);
        novaConsulta.setRecepcionista(consultaOriginal.getRecepcionista());
        novaConsulta.setFormaPagamento(consultaOriginal.getFormaPagamento());

        // Lógica para valor da consulta
        if (consultaOriginal.getConvenio() != null) {
            ConvenioModel convenio = consultaOriginal.getConvenio();
            novaConsulta.setConvenio(convenio);

            // Converta a porcentagem de desconto para BigDecimal para o cálculo
            BigDecimal porcentagemDesconto = convenio.getPorcentagemDesconto();
            BigDecimal valorReferencia = BigDecimal.valueOf(medico.getValorConsultaReferencia());

            BigDecimal valorComDesconto = valorReferencia
                    .multiply(BigDecimal.ONE.subtract(porcentagemDesconto))
                    .setScale(2, RoundingMode.HALF_UP); // Arredondamento para 2 casas decimais

            novaConsulta.setValor(valorComDesconto);
        }

        ConsultaModel consultaSalva = consultaRepository.save(novaConsulta);

        return new ReagendarConsultaResponseDTO("Consulta reagendada com sucesso.", consultaSalva.getDataHoraConsulta());
    }
}
