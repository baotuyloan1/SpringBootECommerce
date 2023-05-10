package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> listProducts();

    Product saveProduct(Product product);

    Page<Product> listAll(int numPage, String sortField, String sortDir, int size);
}
