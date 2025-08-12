package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.model.RecepcionistaModel;
import br.com.smartmed.consultas.rest.dto.AgendaMedicaRequestDTO;
import br.com.smartmed.consultas.rest.dto.AgendaMedicaResponseDTO;
import br.com.smartmed.consultas.rest.dto.MedicoDTO;
import br.com.smartmed.consultas.rest.dto.RecepcionistaDTO;
import br.com.smartmed.consultas.service.ConsultaService;
import br.com.smartmed.consultas.service.MedicoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private ConsultaService consultaService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lista todos os médicos.
     */
    @GetMapping
    public ResponseEntity<List<MedicoDTO>> obterTodos() {
        List<MedicoDTO> medicos = medicoService.obterTodos();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Busca médico por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> buscarPorId(@PathVariable Integer id) {
        MedicoModel medicoModel = medicoService.obterMedicoModelPorId(id);
        MedicoDTO medicoDTO = modelMapper.map(medicoModel, MedicoDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(medicoDTO);
    }

    /**
     * Busca médicos pelo nome (parcial, ignorando case).
     */
    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<MedicoDTO>> buscarPorNome(@RequestParam String nome) {
        List<MedicoDTO> medicos = medicoService.buscarPorNome(nome);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Busca médico pelo CRM (exato).
     */
    @GetMapping("/crm/{crm}")
    public ResponseEntity<MedicoDTO> buscarPorCrm(@PathVariable String crm) {
        MedicoDTO medico = medicoService.buscarPorCrm(crm);
        return ResponseEntity.status(HttpStatus.OK).body(medico);
    }

    /**
     * Cadastra um novo médico.
     */
    @PostMapping
    public ResponseEntity<MedicoDTO> cadastrar(@Valid @RequestBody MedicoModel novoMedico) {
        MedicoDTO novoMedicoDTO = medicoService.salvar(novoMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedicoDTO);
    }

    /**
     * Atualiza dados de um médico.
     */
    @PutMapping
    public ResponseEntity<MedicoDTO> atualizar(@Valid @RequestBody MedicoModel medicoExistente) {
        MedicoDTO atualizado = medicoService.atualizar(medicoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    /**
     * Deleta um médico.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletar(@Valid @RequestBody MedicoModel medico) {
        medicoService.deletar(medico);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para retornar a agenda de um médico para uma data específica.
     * Link: http://localhost:8080/api/medico/agenda
     *
     * @param request DTO com o ID do médico e a data.
     * @return ResponseEntity com a agenda do médico.
     */
    @PostMapping("/agenda")
    public ResponseEntity<AgendaMedicaResponseDTO> obterAgendaMedico(@Valid @RequestBody AgendaMedicaRequestDTO request) {
        AgendaMedicaResponseDTO response = consultaService.obterAgendaMedico(request);
        return ResponseEntity.ok(response);
    }
}
