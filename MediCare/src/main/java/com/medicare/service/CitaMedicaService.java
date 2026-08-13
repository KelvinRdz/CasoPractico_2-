package com.medicare.service;

import com.medicare.domain.CitaMedica;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CitaMedicaService {
    List<CitaMedica> listar();
    Optional<CitaMedica> buscarPorId(Long id);
    CitaMedica guardar(CitaMedica cita);
    CitaMedica actualizar(CitaMedica cita);
    void eliminar(Long id);
    List<CitaMedica> buscarPorActiva(Boolean activa);
    List<CitaMedica> buscarPorRangoFecha(LocalDate inicio, LocalDate fin);
    List<CitaMedica> buscarPorEspecialidad(String especialidad);
}
