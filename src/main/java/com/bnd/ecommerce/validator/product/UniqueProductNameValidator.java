package com.bnd.ecommerce.validator.product;

import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.service.ProductService;
import com.bnd.ecommerce.supplier.ProductServiceSupplier;
import java.util.function.Supplier;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

  private Supplier<ProductService> productServiceSupplier;

  public UniqueProductNameValidator() {}

  @Autowired
  public UniqueProductNameValidator(ProductServiceSupplier productServiceSupplier) {
    this.productServiceSupplier = productServiceSupplier;
  }

  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    if (name == null) return false;
    ProductService productService = productServiceSupplier.get();
    Product product = productService.findByName(name);
    if (product != null) {
      context
          .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
