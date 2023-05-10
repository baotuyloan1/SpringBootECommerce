package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.repository.ProductRepository;
import com.bnd.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> listAll(int numPage, String sortField, String sortDir, int size) {
        Pageable pageable = PageRequest.of(numPage -1, size, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return productRepository.findAll(pageable);
    }


}
