package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.BusinessRuleException;
import br.com.smartmed.consultas.exception.ObjectNotFoundException;
import br.com.smartmed.consultas.exception.SQLException;
import br.com.smartmed.consultas.model.ConsultaModel;
import br.com.smartmed.consultas.rest.dto.EspecialidadeFrequenciaDTO;
import br.com.smartmed.consultas.rest.dto.FaturamentoRequestDTO;
import br.com.smartmed.consultas.rest.dto.FaturamentoResponseDTO;
import br.com.smartmed.consultas.rest.dto.RankingMedicoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações relacionadas a relatórios.
 */
@Service
public class RelatorioService {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public FaturamentoResponseDTO gerarRelatorioFaturamento(FaturamentoRequestDTO request) {
        try {
            // Validação das datas
            if (request.getDataInicio().isAfter(request.getDataFim())) {
                throw new BusinessRuleException("Data de início não pode ser posterior à data de fim.");
            }

            LocalDateTime dataInicio = request.getDataInicio().atStartOfDay();
            LocalDateTime dataFim = request.getDataFim().atTime(LocalTime.MAX);

            List<ConsultaModel> consultas = consultaService.buscarConsultasRealizadasNoPeriodo(dataInicio, dataFim);

            if (consultas.isEmpty()) {
                throw new ObjectNotFoundException("Nenhuma consulta realizada encontrada no período especificado.");
            }

            // Calcula totais
            BigDecimal totalGeral = calcularTotalGeral(consultas);
            List<FaturamentoResponseDTO.FormaPagamentoResumoDTO> porFormaPagamento = calcularPorFormaPagamento(consultas);
            List<FaturamentoResponseDTO.ConvenioResumoDTO> porConvenio = calcularPorConvenio(consultas);

            // Monta resposta
            FaturamentoResponseDTO response = new FaturamentoResponseDTO();
            response.setTotalGeral(totalGeral);
            response.setPorFormaPagamento(porFormaPagamento);
            response.setPorConvenio(porConvenio);

            return response;

        } catch (BusinessRuleException e) {
            throw e; // Re-lança exceções de negócio específicas
        } catch (ObjectNotFoundException e) {
            throw e; // Re-lança exceções de não encontrado
        } catch (Exception e) {
            throw new SQLException("Erro ao gerar relatório de faturamento. " + e.getMessage());
        }
    }

    private BigDecimal calcularTotalGeral(List<ConsultaModel> consultas) {
        return consultas.stream()
                .map(ConsultaModel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<FaturamentoResponseDTO.FormaPagamentoResumoDTO> calcularPorFormaPagamento(List<ConsultaModel> consultas) {
        Map<String, BigDecimal> porFormaPagamento = consultas.stream()
                .filter(c -> c.getFormaPagamento() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getFormaPagamento().getDescricao(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                ConsultaModel::getValor,
                                BigDecimal::add
                        )
                ));

        return porFormaPagamento.entrySet().stream()
                .map(entry -> {
                    FaturamentoResponseDTO.FormaPagamentoResumoDTO dto = new FaturamentoResponseDTO.FormaPagamentoResumoDTO();
                    dto.setFormaPagamento(entry.getKey());
                    dto.setValor(entry.getValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<FaturamentoResponseDTO.ConvenioResumoDTO> calcularPorConvenio(List<ConsultaModel> consultas) {
        Map<String, BigDecimal> porConvenio = consultas.stream()
                .filter(c -> c.getConvenio() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getConvenio().getNome(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                ConsultaModel::getValor,
                                BigDecimal::add
                        )
                ));

        return porConvenio.entrySet().stream()
                .map(entry -> {
                    FaturamentoResponseDTO.ConvenioResumoDTO dto = new FaturamentoResponseDTO.ConvenioResumoDTO();
                    dto.setConvenio(entry.getKey());
                    dto.setValor(entry.getValue());

                    // Adiciona a porcentagem de desconto ao DTO (opcional)
                    consultas.stream()
                            .filter(c -> c.getConvenio() != null && c.getConvenio().getNome().equals(entry.getKey()))
                            .findFirst()
                            .ifPresent(c -> dto.setPorcentagemDesconto(c.getConvenio().getPorcentagemDesconto()));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<EspecialidadeFrequenciaDTO> listarEspecialidadesFrequentes(FaturamentoRequestDTO request) {
        try {
            if (request.getDataInicio().isAfter(request.getDataFim())) {
                throw new BusinessRuleException("A data de início do relatório não pode ser posterior à data de fim.");
            }

            LocalDateTime dataHoraInicio = request.getDataInicio().atStartOfDay();
            LocalDateTime dataHoraFim = request.getDataFim().atTime(LocalTime.MAX);

            List<ConsultaModel> consultas = consultaService.buscarConsultasRealizadasNoPeriodo(dataHoraInicio, dataHoraFim);

            if (consultas.isEmpty()) {
                throw new ObjectNotFoundException("Nenhuma consulta encontrada no período especificado.");
            }

            // Agrupa as consultas por especialidade e conta a quantidade.
            Map<String, Long> contagemPorEspecialidade = consultas.stream()
                    .filter(c -> c.getMedico() != null && c.getMedico().getEspecialidade() != null)
                    .collect(Collectors.groupingBy(
                            c -> c.getMedico().getEspecialidade().getNome(),
                            Collectors.counting()
                    ));

            // Mapeia o resultado para o DTO e ordena por quantidade de forma decrescente.
            return contagemPorEspecialidade.entrySet().stream()
                    .map(entry -> new EspecialidadeFrequenciaDTO(entry.getKey(), entry.getValue()))
                    .sorted(Comparator.comparing(EspecialidadeFrequenciaDTO::getQuantidade).reversed())
                    .collect(Collectors.toList());
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível gerar o relatório. " + e.getMessage());
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível gerar o relatório. " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível gerar o relatório. Falha na conexão com o banco de dados.");
        }
    }

    public Page<RankingMedicoDTO> gerarRankingMedicos(int mes, int ano, Pageable pageable) {
        return consultaService.obterRankingMedicosPorMes(mes, ano, pageable);
    }
}