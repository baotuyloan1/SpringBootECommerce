package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.repository.CategoryRepository;
import com.bnd.ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
