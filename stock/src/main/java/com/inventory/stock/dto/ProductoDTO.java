package com.inventory.stock.dto;


import com.inventory.stock.model.productoModel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class ProductoDTO {
    private long id;
    private String nombre;
    private String  descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String sku;
    private String image_url;
    private Integer status;
    private LocalDateTime fec_inserta;
    private LocalDateTime fec_actualiza;

    public productoModel toEntity() {
        return productoModel.builder()
                .id(this.id)
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .precio(this.precio)
                .stock(this.stock)
                .sku(this.sku)
                .imageUrl(this.image_url)
                .status(this.status)
                .fecInserta(this.fec_inserta)
                .fecActualiza(this.fec_actualiza)
                .build();
    }
}
