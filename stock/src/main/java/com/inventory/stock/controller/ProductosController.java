package com.inventory.stock.controller;

import com.inventory.stock.dto.ProductoDTO;
import com.inventory.stock.model.productoModel;
import com.inventory.stock.service.ProductosServices;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    private final ProductosServices productosServices;

    public ProductosController(ProductosServices productosServices) {
        this.productosServices = productosServices;
    }

    @GetMapping
    public List<ProductoDTO> consultarTodo(){
        return productosServices.consultar_todos_productos();
    }

    @PostMapping
    public ProductoDTO guardarProducto(@RequestBody ProductoDTO producto) {
        return productosServices.guardarProducto(producto);
    }

    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productosServices.actualizarProducto(id, productoDTO);
    }

    @GetMapping("/{id}")
    public ProductoDTO consultar_producto(@PathVariable Long id) {
        return productosServices.consultar_producto(id);
    }

    @DeleteMapping("/{id}")
    public void borrarProducto(@PathVariable Long id) {
        productosServices.borrarProducto(id);
    }


}
