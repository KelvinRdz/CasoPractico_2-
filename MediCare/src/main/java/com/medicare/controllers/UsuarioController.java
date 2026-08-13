package com.medicare.controllers;

import com.medicare.domain.Rol;
import com.medicare.domain.Usuario;
import com.medicare.service.RolService;
import com.medicare.service.UsuarioService;
import com.medicare.serviceimpl.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final MailService mailService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService, MailService mailService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
        this.mailService = mailService;
    }

    @GetMapping
    public String listar(Model model) {
        List<Usuario> lista = usuarioService.listar();
        model.addAttribute("usuarios", lista);
        return "usuario/listado";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.listar());
        return "usuario/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.listar());
            return "usuario/formulario";
        }
        if (usuario.getRol() != null && usuario.getRol().getId() != null) {
            rolService.buscarPorId(usuario.getRol().getId()).ifPresent(usuario::setRol);
        }
        Usuario saved = usuarioService.guardar(usuario);
        // enviar correo de bienvenida (no falla si no hay configuracion)
        mailService.sendWelcome(saved.getEmail(), "Bienvenido a MediCare", "Hola " + saved.getNombre() + "\nGracias por registrarte.");
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario u = usuarioService.buscarPorId(id).orElse(new Usuario());
        model.addAttribute("usuario", u);
        model.addAttribute("roles", rolService.listar());
        return "usuario/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Usuario u = usuarioService.buscarPorId(id).orElse(new Usuario());
        model.addAttribute("usuario", u);
        return "usuario/detalle";
    }
}
