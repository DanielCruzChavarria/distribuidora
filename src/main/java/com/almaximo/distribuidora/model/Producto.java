package com.almaximo.distribuidora.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "clave", nullable = false, unique = true)
    @NotBlank(message = "La clave es obligatoria")
    private String clave;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull
    @Min(0)
    @Column(name = "precio", nullable = false)
    @Positive(message = "El precio debe ser mayor que cero")
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "tipo_producto_id")
    @ToString.Exclude
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ProveedorProducto> proveedores = new ArrayList<>();
}
