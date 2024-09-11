package com.inventory.stock.service;

import com.inventory.stock.dto.ProductoDTO;
import com.inventory.stock.model.productoModel;
import com.inventory.stock.repository.ProductosRepository;
import com.inventory.stock.utils.TransactionsConfig;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductosServices {

    @Resource(name = "getDistributedTransactions")
    private TransactionsConfig transactionsConfig;

    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;

    private final ProductosRepository productosRepository;

    public ProductosServices(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    public List<ProductoDTO> consultar_todos_productos(){
        return productosRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO consultar_producto(Long id) {
        Optional<productoModel> producto = productosRepository.findById(id);
        return producto.map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }


    public ProductoDTO guardarProducto(ProductoDTO producto) {

        productoModel productoGuardado = new productoModel();

        String code = UUID.randomUUID().toString();
        this.transactionsConfig.createTransactionDefinition(code,"Transaction_Dummy");
        this.transactionsConfig.beginTransactions(this.transactionManager);

        try {
            productoGuardado = productosRepository.save(producto.toEntity());

            if(productoGuardado.getId() > 0){
                this.transactionsConfig.commitTransactions();
            }

        } catch (Exception ex){
            this.transactionsConfig.rollbackTransactions();
        }
        return convertToDTO(productoGuardado);
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        productoModel productoActualizado = new productoModel();

        String code = UUID.randomUUID().toString();
        this.transactionsConfig.createTransactionDefinition(code,"Transaction_Dummy");
        this.transactionsConfig.beginTransactions(this.transactionManager);

        Optional<productoModel> productoExistenteOpt = productosRepository.findById(id);
        if (productoExistenteOpt.isEmpty()) {
            this.transactionsConfig.rollbackTransactions();
            throw new RuntimeException("Producto no encontrado con el id: " + id);
        }

        try {
            productoModel productoExistente = productoExistenteOpt.get();

            productoExistente.setNombre(productoDTO.getNombre());
            productoExistente.setDescripcion(productoDTO.getDescripcion());
            productoExistente.setPrecio(productoDTO.getPrecio());
            productoExistente.setStock(productoDTO.getStock());
            productoExistente.setSku(productoDTO.getSku());
            productoExistente.setImageUrl(productoDTO.getImage_url());
            productoExistente.setStatus(productoDTO.getStatus());
            productoExistente.setFecActualiza(LocalDateTime.now());

             productoActualizado = productosRepository.save(productoExistente);
             this.transactionsConfig.commitTransactions();
        } catch (Exception ex){
            this.transactionsConfig.rollbackTransactions();
        }

        return convertToDTO(productoActualizado);
    }


    public void borrarProducto(Long id) {
        String code = UUID.randomUUID().toString();
        this.transactionsConfig.createTransactionDefinition(code,"Transaction_Dummy");
        this.transactionsConfig.beginTransactions(this.transactionManager);

        if (productosRepository.existsById(id)) {
            productosRepository.deleteById(id);
            this.transactionsConfig.commitTransactions();
        } else {
            this.transactionsConfig.rollbackTransactions();
            throw new RuntimeException("Producto no encontrado");
        }
    }

    private ProductoDTO convertToDTO(productoModel producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setSku(producto.getSku());
        dto.setImage_url(producto.getImageUrl());
        dto.setStatus(producto.getStatus());
        dto.setFec_inserta(producto.getFecInserta());
        dto.setFec_actualiza(producto.getFecActualiza());
        return dto;
    }


}
