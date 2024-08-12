package com.almaximo.distribuidora.repository;

import com.almaximo.distribuidora.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

public interface ProveedorRepository  extends JpaRepository<Proveedor, Long> {
    @Transactional
    @Procedure(procedureName = "InsertarProveedor")
    Long insertarProveedor(String nombre, String descripcion);

    @Transactional
    @Procedure(procedureName = "ActualizarProveedor")
    void actualizarProveedor(Long id, String nombre, String descripcion);

    @Transactional
    @Procedure(procedureName = "EliminarProveedor")
    void eliminarProveedor(Long id);
}
