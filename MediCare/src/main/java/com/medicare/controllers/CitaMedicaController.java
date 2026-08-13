package com.medicare.controllers;

import com.medicare.domain.CitaMedica;
import com.medicare.service.CitaMedicaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaMedicaController {

    private final CitaMedicaService citaService;

    public CitaMedicaController(CitaMedicaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public String listar(Model model) {
        List<CitaMedica> lista = citaService.listar();
        model.addAttribute("citas", lista);
        return "cita/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cita", new CitaMedica());
        return "cita/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute CitaMedica cita) {
        citaService.guardar(cita);
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cita", citaService.buscarPorId(id).orElse(new CitaMedica()));
        return "cita/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return "redirect:/citas";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("cita", citaService.buscarPorId(id).orElse(new CitaMedica()));
        return "cita/detalle";
    }
}
