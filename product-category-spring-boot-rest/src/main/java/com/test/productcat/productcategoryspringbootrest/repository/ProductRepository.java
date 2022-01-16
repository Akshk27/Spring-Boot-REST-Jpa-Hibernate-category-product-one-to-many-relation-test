package com.test.productcat.productcategoryspringbootrest.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.productcat.productcategoryspringbootrest.entity.Product;

@Repository
public interface ProductRepository extends  JpaRepository <Product, Integer>{
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Optional<Product> findByIdAndCategoryId(Long id, Long categoryId);
}
