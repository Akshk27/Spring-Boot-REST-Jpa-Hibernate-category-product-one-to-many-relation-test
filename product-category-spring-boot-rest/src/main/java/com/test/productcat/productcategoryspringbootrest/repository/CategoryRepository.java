package com.test.productcat.productcategoryspringbootrest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.productcat.productcategoryspringbootrest.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
