package com.almaximo.distribuidora.service;

import com.almaximo.distribuidora.model.Proveedor;
import com.almaximo.distribuidora.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ProveedorService{

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findById(id);
    }

    @Transactional
    public Proveedor save(Proveedor proveedor) {
        try {
            if (proveedor.getId() == null) {
                // Nuevo proveedor, inserción
                Long proveedorId = proveedorRepository.insertarProveedor(
                        proveedor.getNombre(),
                        proveedor.getDescripcion()
                );
                proveedor.setId(proveedorId);
            } else {
                // Proveedor existente, actualización
                proveedorRepository.actualizarProveedor(
                        proveedor.getId(),
                        proveedor.getNombre(),
                        proveedor.getDescripcion()
                );
            }
            return proveedor;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar o actualizar el proveedor: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            proveedorRepository.eliminarProveedor(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el proveedor: " + e.getMessage(), e);
        }
    }
}
