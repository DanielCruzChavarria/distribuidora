package com.almaximo.distribuidora.repository;

import com.almaximo.distribuidora.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Transactional
    @Procedure(procedureName = "InsertarProducto")
    Long insertarProducto(String clave, String nombre, Double precio, Long tipoProductoId, Boolean active);

    @Transactional
    @Procedure(procedureName = "ActualizarProducto")
    void actualizarProducto(Long id,  String clave, String nombre, Double precio, Long tipoProductoId, Boolean active);

    @Transactional
    @Procedure(procedureName = "EliminarProducto")
    void eliminarProducto(Long id);

    List<Producto> findByActive(boolean active);


    List<Producto> findByClaveContaining(String clave);

    List<Producto> findByTipoProductoId(Long tipoProductoId);

    List<Producto> findByClaveContainingAndTipoProductoId(String clave, Long tipoProductoId);
}
