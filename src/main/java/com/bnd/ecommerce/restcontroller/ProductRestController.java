package com.bnd.ecommerce.restcontroller;

import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.exception.CategoryNotFoundException;
import com.bnd.ecommerce.exception.ErrorDTO;
import com.bnd.ecommerce.exception.handler.GlobalExceptionHandler;
import com.bnd.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
