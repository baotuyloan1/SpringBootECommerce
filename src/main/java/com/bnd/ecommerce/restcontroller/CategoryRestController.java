package com.bnd.ecommerce.restcontroller;

import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.service.CategoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@Validated
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/{id}")
    public Category getOne(@Positive(message = "Category ID must be greater than zero") @PathVariable("id") int id) {
        return categoryService.findById(id);
    }

    @GetMapping("/categories")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Category>> listAll() {
        return null;
    }

    @PostMapping("/categories")
    public Category addOne(@RequestBody @Valid Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void delete(@PathVariable("id") @Positive(message = "Deleted Category ID must be greater than zero") Integer id) {
        categoryService.deleteById(id);
    }





}
