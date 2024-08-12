package com.almaximo.distribuidora.service;

import com.almaximo.distribuidora.model.ProveedorProducto;
import com.almaximo.distribuidora.repository.ProveedorProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProveedorProductoService {

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepository;

    public List<ProveedorProducto> findAll() {
        return proveedorProductoRepository.findAll();
    }

    public Optional<ProveedorProducto> findById(Long id) {
        return proveedorProductoRepository.findById(id);
    }

    public ProveedorProducto save(ProveedorProducto producto) {
        try {
            if (producto.getId() == null) {
                // Nuevo proveedor, inserción
                Long proveedorId = proveedorProductoRepository.insertarProveedorProducto(
                        producto.getClaveProveedor(),
                        producto.getCosto(),
                        producto.getProducto().getId(),
                        producto.getProveedor().getId()
                );
                producto.setId(proveedorId);
            } else {
                // Proveedor existente, actualización
                proveedorProductoRepository.actualizarProveedorProducto(
                        producto.getId(),
                        producto.getClaveProveedor(),
                        producto.getCosto(),
                        producto.getProducto().getId(),
                        producto.getProveedor().getId()
                );
            }
            return producto;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar o actualizar el proveedor del producto: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        proveedorProductoRepository.deleteById(id);
    }

    public List<ProveedorProducto> findByProductoId(Long productoId) {
        return proveedorProductoRepository.findByProductoId(productoId);
    }
}
