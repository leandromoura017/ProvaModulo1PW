package com.example.provamodulo1pw.service;

import com.example.provamodulo1pw.model.Usuario;
import com.example.provamodulo1pw.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public boolean emailExiste(String email) {
        return repo.existsByEmail(email);
    }

    public Usuario salvar(Usuario usuario) {
        return repo.save(usuario);
    }
}
