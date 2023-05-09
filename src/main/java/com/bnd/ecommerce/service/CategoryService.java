package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);

    Category findById(Long id);

    List<Category> listCategories();

    void deleteCategory(long id);
}