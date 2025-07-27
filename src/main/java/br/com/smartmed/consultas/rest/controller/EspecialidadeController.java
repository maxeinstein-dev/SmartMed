package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.EspecialidadeModel;
import br.com.smartmed.consultas.rest.dto.EspecialidadeDTO;
import br.com.smartmed.consultas.service.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidade")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    /**
     * Lista todas as especialidades.
     */
    @GetMapping
    public ResponseEntity<List<EspecialidadeDTO>> obterTodas() {
        List<EspecialidadeDTO> especialidadeDTOList = especialidadeService.obterTodas();
        return ResponseEntity.ok(especialidadeDTOList);
    }

    /**
     * Busca uma especialidade por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeDTO> obterPorId(@PathVariable int id) {
        EspecialidadeDTO especialidadeDTO = especialidadeService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(especialidadeDTO);
    }

    /**
     * Busca especialidades pelo nome.
     */
    @GetMapping("/nome")
    public ResponseEntity<List<EspecialidadeDTO>> buscarPorNome(@RequestParam String nome) {
        List<EspecialidadeDTO> especialidadeDTOList = especialidadeService.buscarPorNome(nome);
        return ResponseEntity.ok(especialidadeDTOList);
    }

    /**
     * Salva uma nova especialidade.
     */
    @PostMapping
    public ResponseEntity<EspecialidadeDTO> salvar(@RequestBody @Valid EspecialidadeModel novaEspecialidade) {
        EspecialidadeDTO especialidadeDTO = especialidadeService.salvar(novaEspecialidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadeDTO);
    }

    /**
     * Atualiza uma especialidade existente.
     */
    @PutMapping
    public ResponseEntity<EspecialidadeDTO> atualizar(@RequestBody @Valid EspecialidadeModel especialidadeExistente) {
        EspecialidadeDTO especialidadeDTO = especialidadeService.atualizar(especialidadeExistente);
        return ResponseEntity.status(HttpStatus.OK).body(especialidadeDTO);
    }

    /**
     * Deleta uma especialidade pelo ID.
     */
    @DeleteMapping
    public void deletar(@RequestBody @Valid EspecialidadeModel especialidadeExistente) {
        especialidadeService.deletar(especialidadeExistente);
    }
}