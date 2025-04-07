package org.hakifiles.api.domain.repositories;

import org.hakifiles.api.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByBlockAsc();
}
