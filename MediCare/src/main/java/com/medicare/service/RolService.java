package com.medicare.service;

import com.medicare.domain.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> listar();
    Optional<Rol> buscarPorId(Long id);
    Rol guardar(Rol rol);
    Rol actualizar(Rol rol);
    void eliminar(Long id);
}
