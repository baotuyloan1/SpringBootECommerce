package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Category;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CategoryService {

  Category saveCategory(Category category);

  Category findById(int id);

  List<Category> listCategories();

  boolean deleteById(int id);

  Page<Category> listAll(int pageNum, String sortField, String sortDir, int size);
}
