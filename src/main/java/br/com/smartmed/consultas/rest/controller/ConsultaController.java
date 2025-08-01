package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.ConsultaModel;
import br.com.smartmed.consultas.rest.dto.*;
import br.com.smartmed.consultas.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador responsável por gerenciar as operações relacionadas às consultas.
 */
@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    /**
     * Instância do serviço de consultas, responsável por encapsular a lógica de negócios
     * e intermediar as operações entre o controlador e o repositório.
     */
    @Autowired
    private ConsultaService consultaService;

    /**
     * Obtém uma consulta pelo ID.
     * Link: http://localhost:8080/api/consulta/{id}
     *
     * @param id ID da consulta.
     * @return consultaDTO representando a consulta encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> obterPorId(@PathVariable Long id) {
        ConsultaDTO consultaDTO = consultaService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(consultaDTO);
    }

    /**
     * Obtém a lista de todas as consultas cadastradas.
     * Link: http://localhost:8080/api/consulta
     *
     * @return Lista de consultaDTO representando as consultas cadastradas.
     */
    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> obterTodas() {
        List<ConsultaDTO> consultaDTOList = consultaService.obterTodas();
        return ResponseEntity.ok(consultaDTOList);
    }

    /**
     * Salva uma nova consulta na base de dados.
     * Link: http://localhost:8080/api/consulta
     *
     * @param novaConsulta ConsultaModel contendo os dados da nova consulta.
     * @return consultaDTO representando a consulta salva.
     */
    @PostMapping
    public ResponseEntity<ConsultaDTO> salvar(@Valid @RequestBody ConsultaModel novaConsulta) {
        ConsultaDTO novaConsultaDTO = consultaService.salvar(novaConsulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConsultaDTO);
    }

    /**
     * Atualiza os dados de uma consulta existente.
     * Link: http://localhost:8080/api/consulta
     *
     * @param consultaExistente ConsultaModel contendo os dados atualizados da consulta.
     * @return consultaDTO representando a consulta atualizada.
     */
    @PutMapping
    public ResponseEntity<ConsultaDTO> atualizar(@Valid @RequestBody ConsultaModel consultaExistente) {
        ConsultaDTO consultaAtualizadaDTO = consultaService.atualizar(consultaExistente);
        return ResponseEntity.status(HttpStatus.OK).body(consultaAtualizadaDTO);
    }

    /**
     * Deleta uma consulta da base de dados.
     * Link: http://localhost:8080/api/consulta
     *
     * @param consulta ConsultaModel contendo os dados da consulta a ser deletada.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletar(@Valid @RequestBody ConsultaModel consulta) {
        consultaService.deletar(consulta);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    /**
     * Endpoint para agendar uma consulta automaticamente.
     * Link: http://localhost:8080/api/consulta/agendar-automatico
     *
     * @param request DTO com os critérios para agendamento automático.
     * @return ResponseEntity com os dados da consulta agendada.
     */
    @PostMapping("/agendar-automatico")
    public ResponseEntity<AgendamentoAutomaticoResponseDTO> agendarAutomaticamente(@Valid @RequestBody AgendamentoAutomaticoRequestDTO request) {
        AgendamentoAutomaticoResponseDTO response = consultaService.agendarAutomaticamente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para retornar o histórico detalhado de consultas de um paciente, permitindo filtros opcionais.
     * Link: http://localhost:8080/api/consulta/historico
     *
     * @param request DTO com os filtros para a consulta do histórico.
     * @return ResponseEntity com a lista de HistoricoConsultaResponseDTO.
     */
    @PostMapping("/historico")
    public ResponseEntity<List<HistoricoConsultaResponseDTO>> obterHistoricoConsultas(@Valid @RequestBody HistoricoConsultaRequestDTO request) {
        List<HistoricoConsultaResponseDTO> historico = consultaService.obterHistoricoConsultas(request);
        return ResponseEntity.ok(historico);
    }

    /**
     * Cancela uma consulta agendada.
     * Link: http://localhost:8080/api/consulta/cancelar
     *
     * @param cancelamentoDTO DTO contendo o ID da consulta e o motivo do cancelamento.
     * @return Resposta com uma mensagem de sucesso e o novo status.
     */
    @PutMapping("/cancelar")
    public ResponseEntity<Map<String, String>> cancelarConsulta(@Valid @RequestBody CancelamentoConsultaDTO cancelamentoDTO) {
        consultaService.cancelarConsulta(cancelamentoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                Map.of(
                        "mensagem", "Consulta cancelada com sucesso",
                        "statusAtual", "CANCELADA"
                )
        );
    }
}