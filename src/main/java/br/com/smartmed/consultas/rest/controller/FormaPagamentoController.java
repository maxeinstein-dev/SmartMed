package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.FormaPagamentoModel;
import br.com.smartmed.consultas.rest.dto.FormaPagamentoDTO;
import br.com.smartmed.consultas.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forma-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    /**
     * Lista todas as formas de pagamento.
     */
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> obterTodas() {
        List<FormaPagamentoDTO> formasDTOList = formaPagamentoService.obterTodas();
        return ResponseEntity.ok(formasDTOList);
    }

    /**
     * Busca uma forma de pagamento por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> obterPorId(@PathVariable int id) {
        FormaPagamentoDTO formaDTO = formaPagamentoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(formaDTO);
    }

    /**
     * Busca formas de pagamento pela descrição.
     */
    @GetMapping("/descricao")
    public ResponseEntity<List<FormaPagamentoDTO>> buscarPorDescricao(@RequestParam String descricao) {
        List<FormaPagamentoDTO> formasDTOList = formaPagamentoService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(formasDTOList);
    }

    /**
     * Cadastra uma nova forma de pagamento.
     */
    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> cadastrar(@Valid @RequestBody FormaPagamentoModel novaForma) {
        FormaPagamentoDTO novaFormaDTO = formaPagamentoService.salvar(novaForma);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFormaDTO);
    }

    /**
     * Atualiza uma forma de pagamento.
     */
    @PutMapping
    public ResponseEntity<FormaPagamentoDTO> atualizar(@Valid @RequestBody FormaPagamentoModel formaExistente) {
        FormaPagamentoDTO formaDTO = formaPagamentoService.atualizar(formaExistente);
        return ResponseEntity.status(HttpStatus.OK).body(formaDTO);
    }

    /**
     * Deleta uma forma de pagamento.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletar(@Valid @RequestBody FormaPagamentoModel formaExistente) {
        formaPagamentoService.deletar(formaExistente);
        return ResponseEntity.noContent().build();
    }
}