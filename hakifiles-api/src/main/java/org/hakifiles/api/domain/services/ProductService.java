package org.hakifiles.api.domain.services;

import org.hakifiles.api.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();

    Product saveProduct(Product product);

    Optional<Product> getProductById(Long id);

    void deleteProduct(Long id);
}
