package com.test.productcat.productcategoryspringbootrest.controller;
import java.net.URI;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.test.productcat.productcategoryspringbootrest.entity.*;
import com.test.productcat.productcategoryspringbootrest.repository.*;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
	private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).body(savedCategory);
    }
    
    //READ
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalCategory.get());
    }
 
    //READ
    @GetMapping
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
    
    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @Valid @RequestBody Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        category.setId(optionalCategory.get().getId());
        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        categoryRepository.delete(optionalCategory.get());

        return ResponseEntity.noContent().build();
    }


}
