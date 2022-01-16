package com.test.productcat.productcategoryspringbootrest.controller;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.*;  
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.test.productcat.productcategoryspringbootrest.entity.Category;
import com.test.productcat.productcategoryspringbootrest.entity.Product;
import com.test.productcat.productcategoryspringbootrest.repository.CategoryRepository;
import com.test.productcat.productcategoryspringbootrest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;  
import java.net.URI;  
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {  
	private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        Optional<Category> optionalCategory = categoryRepository.findById(product.getCategory().getId());
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        product.setCategory(optionalCategory.get());

        Product savedProduct = productRepository.save(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).body(savedProduct);
    }

    //READ
    @GetMapping
    public ResponseEntity<Page<Product>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalProduct.get());
    }
    
    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody @Valid Product product, @PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(product.getCategory().getId());
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        product.setCategory(optionalCategory.get());
        product.setId(optionalProduct.get().getId());
        productRepository.save(product);

        return ResponseEntity.noContent().build();
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        productRepository.delete(optionalProduct.get());

        return ResponseEntity.noContent().build();
    }

}
