package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.RecepcionistaModel;
import br.com.smartmed.consultas.rest.dto.PageResponseDTO;
import br.com.smartmed.consultas.rest.dto.RecepcionistaDTO;
import br.com.smartmed.consultas.rest.dto.RecepcionistaFilterRequestDTO;
import br.com.smartmed.consultas.service.RecepcionistaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/recepcionista")
public class RecepcionistaController {
    /**
     * Instância do serviço de recepcionistas, responsável por encapsular a lógica de negócios
     * e intermediar as operações entre o controlador e o repositório.
     */
    @Autowired
    private RecepcionistaService recepcionistaService;

    /**
     * Obtém uma recepcionista pelo ID.
     * Link: http://localhost:8080/api/recepcionista/?
     *
     * @param id ID do recepcionista.
     * @return recepcionistaDTO representando a recepcionista encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecepcionistaDTO> obterPorId(@PathVariable int id) {
        RecepcionistaDTO recepcionistaDTO = recepcionistaService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(recepcionistaDTO);
    }

    /**
     * Obtém a lista de todas as recepcionistas cadastradas.
     * Link: http://localhost:8080/api/recepcionista
     *
     * @return Lista de recepcionistaDTO representando as recepcionista cadastradas.
     */
    @GetMapping()
    public ResponseEntity<List<RecepcionistaDTO>> obterTodos() {
        List<RecepcionistaDTO> recepcionistaDTOList = recepcionistaService.obterTodos();
        return ResponseEntity.ok(recepcionistaDTOList);
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<RecepcionistaDTO>> buscarPorNome(@RequestParam String nome) {
        List<RecepcionistaDTO> RecepcionistaDTOList = recepcionistaService.buscarPorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(RecepcionistaDTOList);
    }

    @GetMapping("/buscar-por-cpf")
    public ResponseEntity<RecepcionistaDTO> buscarPorCpf(@RequestParam String cpf) {
        RecepcionistaDTO RecepcionistaDTO = recepcionistaService.buscarPorCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(RecepcionistaDTO);
    }

    /**
     * Salva uma nova recepcionista na base de dados.
     * Link: http://localhost:8080/api/recepcionista
     *
     * @param novaRecepcionista RecepcionistaModel contendo os dados da nova recepcionista.
     * @return recepcionistaDTO representando a recepcionista salva.
     */
    @PostMapping()
    public ResponseEntity<RecepcionistaDTO> salvar(@Valid @RequestBody RecepcionistaModel novaRecepcionista) {
        RecepcionistaDTO novaRecepcionistaDTO = recepcionistaService.salvar(novaRecepcionista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRecepcionistaDTO);
    }

    /**
     * Atualiza os dados de uma recepcionista existente.
     * Link: http://localhost:8080/api/recepcionista
     *
     * @param recepcionistaExistente recepcionistaModel contendo os dados atualizados da recepcionista.
     * @return recepcionistaExistenteDTO representando a recepcionista atualizado.
     * @link http://localhost:8080/api/recepcionista
     */
    @PutMapping
    public ResponseEntity<RecepcionistaDTO> atualizar(@Valid @RequestBody RecepcionistaModel recepcionistaExistente) {
        RecepcionistaDTO recepcionistaExistenteDTO = recepcionistaService.atualizar(recepcionistaExistente);
        return ResponseEntity.status(HttpStatus.OK).body(recepcionistaExistenteDTO);
    }

    /**
     * Deleta uma recepcionista da base de dados.
     * Link: http://localhost:8080/api/recepcionista
     *
     * @param recepcionistaExistente recepcionistaModel contendo os dados da recepcionista a ser deletado.
     * @link http://localhost:8080/api/recepcionista
     */
    @DeleteMapping
    public void deletar(@Valid @RequestBody RecepcionistaModel recepcionistaExistente) {
        recepcionistaService.deletar(recepcionistaExistente);
    }

    /**
     * Lista recepcionistas com filtros e paginação.
     * Link: http://localhost:8080/api/recepcionista/filtrar
     *
     * @param filter DTO com os critérios de filtro e paginação.
     * @return Uma resposta paginada com a lista de recepcionistas.
     */
    @PostMapping("/filtrar")
    public ResponseEntity<PageResponseDTO<RecepcionistaDTO>> listarComFiltroEPaginacao(@Valid @RequestBody RecepcionistaFilterRequestDTO filter) {
        PageResponseDTO<RecepcionistaDTO> response = recepcionistaService.listarComFiltroEPaginacao(filter);
        return ResponseEntity.ok(response);
    }
}
