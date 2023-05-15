package com.bnd.ecommerce.restcontroller;

import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public Product addOne(@Valid @RequestBody Product product){
        return productService.saveProduct(product);
    }

}
