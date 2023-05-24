package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.entity.Product;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

public interface ProductService {

  List<Product> listProducts();

  Product saveProduct(Product product);

  Page<Product> listAll(int numPage, String sortField, String sortDir, int size, String keyword);

  Object findById(long id);

  boolean deleteProductById(long id);

  Category findRootCategory(Category category, Set<Category> categories);

  Product findByName(String name);
}
