package com.application.rest.persistence;

import com.application.rest.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductDAO {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByPreceInRange(BigDecimal minPrecio, BigDecimal maxPrecio);

    void save(Product product);

    void deleteById(Long id);
}
