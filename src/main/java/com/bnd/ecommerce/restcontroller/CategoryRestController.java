package com.bnd.ecommerce.restcontroller;

import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.service.CategoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@Validated
public class CategoryRestController {

    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/{id}")
    public Category getOne(@Positive @PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/categories")
    public CollectionModel<EntityModel<Category>> listAll() {
        return null;
    }

    @PostMapping("/categories")
    public Category addOne(@RequestBody @Valid Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void delete(@PathVariable("id") @Positive Integer id) {
        categoryService.deleteCategory(id);
    }
}
