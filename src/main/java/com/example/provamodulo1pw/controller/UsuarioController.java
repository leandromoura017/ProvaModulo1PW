package com.example.provamodulo1pw.controller;

import com.example.provamodulo1pw.model.Usuario;
import com.example.provamodulo1pw.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/cadastro")
    public String formCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(HttpServletRequest request, HttpServletResponse response, Model model) {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");

        // Validação simples
        if (nome == null || nome.isBlank() || email == null || email.isBlank() || !email.contains("@")) {
            model.addAttribute("erro", "Dados inválidos. Verifique o nome e o e-mail.");
            model.addAttribute("usuario", new Usuario()); // reexibe formulário
            return "cadastro";
        }

        if (service.emailExiste(email)) {
            model.addAttribute("erro", "E-mail já cadastrado.");
            model.addAttribute("usuario", new Usuario());
            return "cadastro";
        }

        // Cria e salva usuário
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        Usuario salvo = service.salvar(usuario);

        // Inicia sessão e define atributo
        request.getSession(true).setAttribute("usuarioId", salvo.getId());

        return "redirect:/dashboard";
    }


    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
        Object usuarioId = request.getSession(false) != null ? request.getSession(false).getAttribute("usuarioId") : null;

        if (usuarioId == null) {
            return "redirect:/cadastro";
        }

        model.addAttribute("mensagem", "Bem-vindo ao dashboard!");
        return "dashboard";
    }
}