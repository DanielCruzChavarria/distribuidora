package com.almaximo.distribuidora.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.ToString;

@Entity
@Table(name = "proveedor_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @ToString.Exclude
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    @ToString.Exclude
    private Proveedor proveedor;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "clave_proveedor", nullable = false)
    private String claveProveedor;

    @NotNull
    @Min(0)
    @Column(name = "costo", nullable = false)
    private Double costo;
}
