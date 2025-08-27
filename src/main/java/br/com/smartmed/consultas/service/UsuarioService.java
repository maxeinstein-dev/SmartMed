package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.model.RecepcionistaModel;
import br.com.smartmed.consultas.model.UsuarioModel;
import br.com.smartmed.consultas.repository.UsuarioRepository;
import br.com.smartmed.consultas.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private RecepcionistaService recepcionistaService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public CadastroUsuarioResponseDTO cadastrar(CadastroUsuarioRequestDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um usuário com este email.");
        }

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());

        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(senhaCriptografada);
        usuario.setPerfil(request.getPerfil());
        usuarioRepository.save(usuario);

        switch (usuario.getPerfil()) {
            case MEDICO:
                medicoService.vincularUsuario(usuario);
                break;
            case RECEPCIONISTA:
                recepcionistaService.vincularUsuario(usuario);
                break;
            default:
                // Nenhum vínculo necessário para outros perfis (ex: PACIENTE, ADMIN)
                break;
        }

        CadastroUsuarioResponseDTO response = new CadastroUsuarioResponseDTO();
        response.setMensagem("Usuário cadastrado com sucesso");
        response.setUsuarioID(usuario.getId());

        return response;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        // 1. Buscar usuário pelo e-mail
        UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos."));

        // 2. Comparar a senha enviada com a senha criptografada
        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("E-mail ou senha inválidos.");
        }

        // 3. Se a validação for bem-sucedida, criar a resposta
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMensagem("Login realizado com sucesso");
        response.setPerfil(usuario.getPerfil());
        response.setToken(UUID.randomUUID().toString().replace("-", ""));

        // 4. Buscar e adicionar os dados detalhados do usuário
        switch (usuario.getPerfil()) {
            case MEDICO:
                MedicoModel medico = medicoService.obterMedicoPorUsuarioId(usuario.getId());
                response.setDadosDoUsuario(new MedicoResponseDTO(medico));
                break;
            case RECEPCIONISTA:
                RecepcionistaModel recepcionista = recepcionistaService.obterRecepcionistaPorUsuarioId(usuario.getId());
                response.setDadosDoUsuario(new RecepcionistaResponseDTO(recepcionista));
                break;
            default:
                // Se o perfil não for médico ou recepcionista, pode ser nulo ou um DTO genérico
                response.setDadosDoUsuario(null);
                break;
        }

        return response;
    }
}
