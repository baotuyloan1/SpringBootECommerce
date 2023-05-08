package com.bnd.ecommerce.restcontroller;

import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public Category saveCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }
}
