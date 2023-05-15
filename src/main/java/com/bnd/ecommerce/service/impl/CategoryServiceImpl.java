package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.exception.NotFoundException;
import com.bnd.ecommerce.repository.CategoryRepository;
import com.bnd.ecommerce.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public Category findById(Long id) {
    Optional<Category> category = categoryRepository.findById(id);
    if (category.isPresent()) {
      return category.get();
    } else {
      throw new NotFoundException("Category not found");
    }
  }

  @Override
  public List<Category> listCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public void deleteCategory(long id) {
    categoryRepository.deleteById(id);
  }

  public Page<Category> listAll(int pageNum, String sortField, String sortDir, int size) {
    Pageable pageable =
        PageRequest.of(
            pageNum - 1,
            size,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    return categoryRepository.findAll(pageable);
  }
}
