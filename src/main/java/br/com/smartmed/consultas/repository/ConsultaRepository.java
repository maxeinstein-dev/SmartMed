package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

//Long pois o ID é BigInt
public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {

    /**
     * Busca todas as consultas de um paciente específico.
     *
     * @param pacienteId ID do paciente.
     * @return Lista de consultas.
     */
    List<ConsultaModel> findByPacienteId(Integer pacienteId);

    /**
     * Busca todas as consultas de um médico específico.
     *
     * @param medicoId ID do médico.
     * @return Lista de consultas.
     */
    List<ConsultaModel> findByMedicoId(Integer medicoId);

    /**
     * Busca consultas por status.
     *
     * @param status Status da consulta (ex.: "AGENDADA", "CANCELADA").
     * @return Lista de consultas.
     */
    List<ConsultaModel> findByStatus(String status);

    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.status = 'REALIZADA' " +
            "AND c.dataHoraConsulta BETWEEN :dataInicio AND :dataFim " +
            "ORDER BY c.dataHoraConsulta")
    List<ConsultaModel> findConsultasRealizadasByPeriodo(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.medico.id = :medicoId " +
            "AND c.dataHoraConsulta >= :dataInicio " +
            "AND c.dataHoraConsulta < :dataFim")
    List<ConsultaModel> findConsultasByMedicoAndPeriod(
            @Param("medicoId") Integer medicoId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.paciente.id = :pacienteId " +
            "AND (:dataInicio IS NULL OR c.dataHoraConsulta >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataHoraConsulta <= :dataFim) " +
            "AND (:medicoId IS NULL OR c.medico.id = :medicoId) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:especialidadeId IS NULL OR c.medico.especialidade.id = :especialidadeId)")
    List<ConsultaModel> findHistoricoConsultas(
            @Param("pacienteId") Integer pacienteId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            @Param("medicoId") Integer medicoId,
            @Param("status") String status,
            @Param("especialidadeId") Integer especialidadeId
    );

    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.medico.id = :medicoId " +
            "AND c.dataHoraConsulta >= :inicioDoDia " +
            "AND c.dataHoraConsulta <= :fimDoDia")
    List<ConsultaModel> findConsultasByMedicoAndDate(
            @Param("medicoId") Integer medicoId,
            @Param("inicioDoDia") LocalDateTime inicioDoDia,
            @Param("fimDoDia") LocalDateTime fimDoDia
    );

    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.status = 'REALIZADA' " +
            "AND c.dataHoraConsulta BETWEEN :dataInicio AND :dataFim " +
            "AND (:medicoId IS NULL OR c.medico.id = :medicoId) " +
            "AND (:pacienteId IS NULL OR c.paciente.id = :pacienteId) " +
            "AND (:formaPagamentoId IS NULL OR c.formaPagamento.id = :formaPagamentoId) " +
            "AND (:convenioId IS NULL OR c.convenio.id = :convenioId)")
    List<ConsultaModel> findByFaturamentoPeriodo(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            @Param("medicoId") Integer medicoId,
            @Param("pacienteId") Integer pacienteId,
            @Param("formaPagamentoId") Integer formaPagamentoId,
            @Param("convenioId") Integer convenioId
    );
}
