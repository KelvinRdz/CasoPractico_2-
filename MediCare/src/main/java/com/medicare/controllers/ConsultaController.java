package com.medicare.controllers;

import com.medicare.domain.CitaMedica;
import com.medicare.service.CitaMedicaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ConsultaController {

    private final CitaMedicaService citaService;

    public ConsultaController(CitaMedicaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping("/consultas")
    public String consultas(Model model,
                            @RequestParam(required = false) Boolean activa,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                            @RequestParam(required = false) String especialidad) {

        if (activa != null) {
            model.addAttribute("resultados", citaService.buscarPorActiva(activa));
        } else if (inicio != null && fin != null) {
            model.addAttribute("resultados", citaService.buscarPorRangoFecha(inicio, fin));
        } else if (especialidad != null && !especialidad.isBlank()) {
            model.addAttribute("resultados", citaService.buscarPorEspecialidad(especialidad));
        } else {
            model.addAttribute("resultados", List.of());
        }

        return "consultas/consultas";
    }
}
