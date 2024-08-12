package com.almaximo.distribuidora.repository;

import com.almaximo.distribuidora.model.ProveedorProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProveedorProductoRepository  extends JpaRepository<ProveedorProducto, Long>{
    List<ProveedorProducto> findByProductoId(Long productoId);
    @Transactional
    @Procedure(procedureName = "InsertarProveedorProducto")
    Long insertarProveedorProducto(String claveProveedor, Double costo, Long productoId, Long proveedorId);

    @Transactional
    @Procedure(procedureName = "ActualizarProveedorProducto")
    void actualizarProveedorProducto(Long proveedorProductoId, String claveProveedor, Double costo, Long productoId, Long proveedorId);
}
