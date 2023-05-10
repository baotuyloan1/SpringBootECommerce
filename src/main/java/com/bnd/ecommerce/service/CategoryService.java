package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);

    Category findById(Long id);

    List<Category> listCategories();

    void deleteCategory(long id);

    Page<Category> listAll(int pageNum, String sortField, String sortDir, int size);
}