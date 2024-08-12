package com.almaximo.distribuidora.controller;

import com.almaximo.distribuidora.exception.ResourceNotFoundException;
import com.almaximo.distribuidora.model.TipoProducto;
import com.almaximo.distribuidora.service.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    @GetMapping
    public List<TipoProducto> getAllTiposProducto() {
        return tipoProductoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProducto> getTipoProductoById(@PathVariable Long id) {
        TipoProducto tipoProducto = tipoProductoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Producto no encontrado con id: " + id));
        return ResponseEntity.ok(tipoProducto);
    }

    @PostMapping
    public ResponseEntity<TipoProducto> createTipoProducto(@Valid @RequestBody TipoProducto tipoProducto) {
        TipoProducto savedTipoProducto = tipoProductoService.save(tipoProducto);
        return ResponseEntity.ok(savedTipoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProducto> updateTipoProducto(@PathVariable Long id, @Valid @RequestBody TipoProducto tipoProducto) {
        TipoProducto existingTipoProducto = tipoProductoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Producto no encontrado con id: " + id));
        tipoProducto.setId(existingTipoProducto.getId());
        TipoProducto updatedTipoProducto = tipoProductoService.save(tipoProducto);
        return ResponseEntity.ok(updatedTipoProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoProducto(@PathVariable Long id) {
        TipoProducto tipoProducto = tipoProductoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Producto no encontrado con id: " + id));
        tipoProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
