package com.application.rest.repository;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.precio BETWEEN ?1 and ?2")
    List<Product> findProductByPrecioRange(BigDecimal minPrecio, BigDecimal maxPrecio);
    List<Product> findProductByPrecioBetween(BigDecimal minPrecio, BigDecimal maxPrecio);
}
