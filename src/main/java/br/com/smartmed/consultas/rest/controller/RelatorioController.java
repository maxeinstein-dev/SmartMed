package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.rest.dto.FaturamentoRequestDTO;
import br.com.smartmed.consultas.rest.dto.FaturamentoResponseDTO;
import br.com.smartmed.consultas.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
}