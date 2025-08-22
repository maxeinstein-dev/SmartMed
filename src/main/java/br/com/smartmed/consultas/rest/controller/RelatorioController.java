package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.rest.dto.*;
import br.com.smartmed.consultas.service.ConsultaService;
import br.com.smartmed.consultas.service.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;
    @Autowired
    private ConsultaService consultaService;

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
    public ResponseEntity<PageResponseDTO<RankingMedicoDTO>> gerarRankingMedicos(@RequestBody @Valid RankingMedicoRequestDTO request) {
        PageResponseDTO<RankingMedicoDTO> ranking = consultaService.gerarRankingMedicos(request);
        return ResponseEntity.ok(ranking);
    }
}