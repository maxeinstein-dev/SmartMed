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
    List<ConsultaModel> findConsultasRealizadasNoPeriodo(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);

    /**
     * Busca todas as consultas agendadas ou realizadas de um médico dentro de um período.
     * A lógica de sobreposição será inteiramente realizada no Service.
     *
     * @param medicoId ID do médico.
     * @param inicioPeriodo Início do período a ser consultado.
     * @param fimPeriodo Fim do período a ser consultado.
     * @return Lista de consultas do médico dentro do período especificado.
     */
    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.medico.id = :medicoId " +
            "AND c.status IN ('AGENDADA', 'REALIZADA') " +
            "AND c.dataHoraConsulta BETWEEN :inicioPeriodo AND :fimPeriodo " +
            "ORDER BY c.dataHoraConsulta")
    List<ConsultaModel> findConsultasByMedicoAndPeriod(
            @Param("medicoId") Integer medicoId,
            @Param("inicioPeriodo") LocalDateTime inicioPeriodo,
            @Param("fimPeriodo") LocalDateTime fimPeriodo);

    /**
     * Busca o histórico detalhado de consultas de um paciente com filtros opcionais.
     *
     * @param pacienteId ID do paciente.
     * @param dataInicio Início do intervalo de datas (opcional).
     * @param dataFim Fim do intervalo de datas (opcional).
     * @param medicoId ID do médico específico (opcional).
     * @param status Status da consulta (opcional).
     * @param especialidadeId ID da especialidade (opcional).
     * @return Lista de consultas que correspondem aos critérios.
     */
    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.paciente.id = :pacienteId " +
            "AND (:dataInicio IS NULL OR c.dataHoraConsulta >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataHoraConsulta <= :dataFim) " +
            "AND (:medicoId IS NULL OR c.medico.id = :medicoId) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:especialidadeId IS NULL OR c.medico.especialidade.id = :especialidadeId) " +
            "ORDER BY c.dataHoraConsulta DESC")
    List<ConsultaModel> findHistoricoConsultas(
            @Param("pacienteId") Integer pacienteId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            @Param("medicoId") Integer medicoId,
            @Param("status") String status,
            @Param("especialidadeId") Integer especialidadeId);

    /**
     * Busca todas as consultas de um médico em uma data específica.
     *
     * @param medicoId ID do médico.
     * @param dataInicio Dia completo (início do dia).
     * @param dataFim Dia completo (fim do dia).
     * @return Lista de consultas do médico para a data.
     */
    @Query("SELECT c FROM ConsultaModel c " +
            "WHERE c.medico.id = :medicoId " +
            "AND c.dataHoraConsulta BETWEEN :dataInicio AND :dataFim " +
            "AND c.status IN ('AGENDADA', 'REALIZADA') " +
            "ORDER BY c.dataHoraConsulta ASC")
    List<ConsultaModel> findConsultasByMedicoAndDate(
            @Param("medicoId") Integer medicoId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);
}
