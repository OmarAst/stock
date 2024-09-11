package com.inventory.stock.repository;

import com.inventory.stock.model.productoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<productoModel, Long> {
}
