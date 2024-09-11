package com.inventory.stock.test;

import com.inventory.stock.controller.ProductosController;
import com.inventory.stock.dto.ProductoDTO;
import com.inventory.stock.service.ProductosServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductosControllerTest {

    @Mock
    private ProductosServices productosServices;

    @InjectMocks
    private ProductosController productosController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsultarTodo() {
        ProductoDTO producto1 = new ProductoDTO();
        ProductoDTO producto2 = new ProductoDTO();
        List<ProductoDTO> productos = Arrays.asList(producto1, producto2);

        when(productosServices.consultar_todos_productos()).thenReturn(productos);

        List<ProductoDTO> result = productosController.consultarTodo();

        assertEquals(productos, result);
        verify(productosServices, times(1)).consultar_todos_productos();
    }

    @Test
    public void testGuardarProducto() {
        ProductoDTO producto = new ProductoDTO();
        when(productosServices.guardarProducto(any(ProductoDTO.class))).thenReturn(producto);

        ProductoDTO result = productosController.guardarProducto(producto);

        assertEquals(producto, result);
        verify(productosServices, times(1)).guardarProducto(any(ProductoDTO.class));
    }

    @Test
    public void testActualizarProducto() {
        Long id = 1L;
        ProductoDTO productoDTO = new ProductoDTO();
        when(productosServices.actualizarProducto(anyLong(), any(ProductoDTO.class))).thenReturn(productoDTO);

        ProductoDTO result = productosController.actualizarProducto(id, productoDTO);

        assertEquals(productoDTO, result);
        verify(productosServices, times(1)).actualizarProducto(anyLong(), any(ProductoDTO.class));
    }

    @Test
    public void testConsultarProducto() {
        Long id = 1L;
        ProductoDTO productoDTO = new ProductoDTO();
        when(productosServices.consultar_producto(anyLong())).thenReturn(productoDTO);

        ProductoDTO result = productosController.consultar_producto(id);

        assertEquals(productoDTO, result);
        verify(productosServices, times(1)).consultar_producto(anyLong());
    }

    @Test
    public void testBorrarProducto() {
        Long id = 1L;

        productosController.borrarProducto(id);

        verify(productosServices, times(1)).borrarProducto(anyLong());
    }
}
