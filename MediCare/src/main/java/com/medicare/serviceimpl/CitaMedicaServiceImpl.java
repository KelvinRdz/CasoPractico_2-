package com.medicare.serviceimpl;

import com.medicare.domain.CitaMedica;
import com.medicare.repository.CitaMedicaRepository;
import com.medicare.service.CitaMedicaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CitaMedicaServiceImpl implements CitaMedicaService {

    private final CitaMedicaRepository citaRepository;

    public CitaMedicaServiceImpl(CitaMedicaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public List<CitaMedica> listar() {
        return citaRepository.findAll();
    }

    @Override
    public Optional<CitaMedica> buscarPorId(Long id) {
        return citaRepository.findById(id);
    }

    @Override
    public CitaMedica guardar(CitaMedica cita) {
        return citaRepository.save(cita);
    }

    @Override
    public CitaMedica actualizar(CitaMedica cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<CitaMedica> buscarPorActiva(Boolean activa) {
        return citaRepository.findByActiva(activa);
    }

    @Override
    public List<CitaMedica> buscarPorRangoFecha(LocalDate inicio, LocalDate fin) {
        return citaRepository.findByFechaBetween(inicio, fin);
    }

    @Override
    public List<CitaMedica> buscarPorEspecialidad(String especialidad) {
        return citaRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }
}
