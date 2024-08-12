package com.almaximo.distribuidora.service;

import com.almaximo.distribuidora.model.Producto;
import com.almaximo.distribuidora.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    public List<Producto> findAllActive() {
        return productoRepository.findByActive(true);
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            // Nuevo producto, inserción
            Long productoId = productoRepository.insertarProducto(
                    producto.getClave(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getTipoProducto().getId(),
                    producto.isActive()
            );
            producto.setId(productoId);
        } else {
            // Producto existente, actualización
            productoRepository.actualizarProducto(
                    producto.getId(),
                    producto.getClave(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getTipoProducto().getId(),
                    producto.isActive()
            );
        }
        return producto;
    }

    @Transactional
    public void deleteById(Long id) {
        productoRepository.eliminarProducto(id);
    }

    public List<Producto> findByClaveAndTipoProducto(String clave, Long tipoProductoId) {
        if (clave != null && tipoProductoId != null) {
            return productoRepository.findByClaveContainingAndTipoProductoId(clave, tipoProductoId);
        } else if (clave != null) {
            return productoRepository.findByClaveContaining(clave);
        } else if (tipoProductoId != null) {
            return productoRepository.findByTipoProductoId(tipoProductoId);
        } else {
            return productoRepository.findAll();
        }
    }
}
