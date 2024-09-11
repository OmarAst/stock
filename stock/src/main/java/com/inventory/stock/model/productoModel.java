package com.inventory.stock.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class productoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status", length = 20)
    private Integer status;

    @Column(name = "fec_inserta", updatable = false)
    private LocalDateTime fecInserta;

    @Column(name = "fec_actualiza")
    private LocalDateTime fecActualiza;


}
