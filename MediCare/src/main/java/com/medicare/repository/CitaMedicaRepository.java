package com.medicare.repository;

import com.medicare.domain.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {
    List<CitaMedica> findByActiva(Boolean activa);
    List<CitaMedica> findByEspecialidadContainingIgnoreCase(String especialidad);
    List<CitaMedica> findByFechaBetween(LocalDate inicio, LocalDate fin);

    @Query("SELECT c FROM CitaMedica c WHERE c.costo >= :min AND c.costo <= :max")
    List<CitaMedica> findByCostoBetween(@Param("min") Double min, @Param("max") Double max);
}
