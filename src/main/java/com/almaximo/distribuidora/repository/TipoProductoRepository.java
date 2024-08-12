package com.almaximo.distribuidora.repository;

import com.almaximo.distribuidora.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
    @Transactional
    @Procedure(procedureName = "InsertarTipoProducto")
    Long insertarTipoProducto(String nombre, String descripcion);

    @Transactional
    @Procedure(procedureName = "ActualizarTipoProducto")
    void actualizarTipoProducto(Long id, String nombre, String descripcion);

    @Transactional
    @Procedure(procedureName = "EliminarTipoProducto")
    void eliminarTipoProducto(Long id);
}
