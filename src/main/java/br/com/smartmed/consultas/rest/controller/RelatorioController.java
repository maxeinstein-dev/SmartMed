package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.rest.dto.*;
import br.com.smartmed.consultas.service.RelatorioService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/faturamento")
    public ResponseEntity<FaturamentoResponseDTO> getFaturamento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        FaturamentoRequestDTO request = new FaturamentoRequestDTO();
        request.setDataInicio(dataInicio);
        request.setDataFim(dataFim);

        FaturamentoResponseDTO response = relatorioService.gerarRelatorioFaturamento(request);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/especialidades-frequentes")
    public ResponseEntity<List<EspecialidadeFrequenciaDTO>> listarEspecialidadesFrequentes(@Valid @RequestBody FaturamentoRequestDTO request) {
        List<EspecialidadeFrequenciaDTO> especialidades = relatorioService.listarEspecialidadesFrequentes(request);
        return ResponseEntity.ok(especialidades);
    }

    @PostMapping("/medicos-mais-ativos")
    public ResponseEntity<Page<RankingMedicoDTO>> getRankingMedicosMaisAtivos(
            @RequestBody @Valid RelatorioMedicoRequestDTO request,
            @ParameterObject @PageableDefault(size = 10, sort = "quantidadeConsultas", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<RankingMedicoDTO> ranking = relatorioService.gerarRankingMedicos(request.getMes(), request.getAno(), pageable);
        return ResponseEntity.ok(ranking);
    }
}