package com.bnd.ecommerce.supplier;

import com.bnd.ecommerce.service.ProductService;
import java.util.function.Supplier;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceSupplier implements Supplier<ProductService> {
  private final ApplicationContext applicationContext;

  public ProductServiceSupplier(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public ProductService get() {
    return applicationContext.getBean(ProductService.class);
  }
}
