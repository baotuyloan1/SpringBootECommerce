package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProducts();

    Product saveProduct(Product product);
}
