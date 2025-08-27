package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.ConvenioModel;
import br.com.smartmed.consultas.rest.dto.ConvenioDTO;
import br.com.smartmed.consultas.service.ConvenioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convenio")
public class ConvenioController {

    @Autowired
    private ConvenioService convenioService;

    @GetMapping
    public ResponseEntity<List<ConvenioDTO>> obterTodos() {
        List<ConvenioDTO> convenioDTOList = convenioService.obterTodos();
        return ResponseEntity.ok(convenioDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConvenioDTO> obterPorId(@PathVariable int id) {
        ConvenioDTO convenioDTO = convenioService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(convenioDTO);
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<ConvenioDTO>> buscarPorNome(@RequestParam String nome) {
        List<ConvenioDTO> convenioDTOList = convenioService.buscarPorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(convenioDTOList);
    }

    @GetMapping("/buscar-por-cnpj")
    public ResponseEntity<ConvenioDTO> buscarPorCnpj(@RequestParam String cnpj) {
        ConvenioDTO convenioDTO = convenioService.buscarPorCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(convenioDTO);
    }

    @PostMapping
    public ResponseEntity<ConvenioDTO> cadastrar(@Valid @RequestBody ConvenioModel novoConvenio) {
        ConvenioDTO novoConvenioDTO = convenioService.salvar(novoConvenio);
        return ResponseEntity.ok(novoConvenioDTO);
    }

    @PutMapping
    public ResponseEntity<ConvenioDTO> atualizar(@Valid @RequestBody ConvenioModel convenioExistente) {
        ConvenioDTO convenioExistenteDTO = convenioService.atualizar(convenioExistente);
        return ResponseEntity.status(HttpStatus.OK).body(convenioExistenteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        convenioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
