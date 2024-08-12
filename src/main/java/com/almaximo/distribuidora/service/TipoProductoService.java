package com.almaximo.distribuidora.service;

import com.almaximo.distribuidora.model.TipoProducto;
import com.almaximo.distribuidora.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProductoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    public List<TipoProducto> findAll() {
        return tipoProductoRepository.findAll();
    }

    public Optional<TipoProducto> findById(Long id) {
        return tipoProductoRepository.findById(id);
    }

    @Transactional
    public TipoProducto save(TipoProducto tipoProducto) {
        try {
            if (tipoProducto.getId() == null) {
                // Nuevo tipo de producto, inserción
                Long tipoProductoId = tipoProductoRepository.insertarTipoProducto(
                        tipoProducto.getNombre(),
                        tipoProducto.getDescripcion()
                );
                tipoProducto.setId(tipoProductoId);
            } else {
                // Tipo de producto existente, actualización
                tipoProductoRepository.actualizarTipoProducto(
                        tipoProducto.getId(),
                        tipoProducto.getNombre(),
                        tipoProducto.getDescripcion()
                );
            }
            return tipoProducto;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar o actualizar el tipo de producto: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            tipoProductoRepository.eliminarTipoProducto(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el tipo de producto: " + e.getMessage(), e);
        }
    }
}
