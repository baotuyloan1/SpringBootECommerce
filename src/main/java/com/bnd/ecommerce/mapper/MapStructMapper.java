package com.bnd.ecommerce.mapper;

import com.bnd.ecommerce.dto.*;
import com.bnd.ecommerce.entity.Brand;
import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.entity.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

  EmployeeUpdateDto employeeToEmployeePostDto(Employee employee);

  Employee employeeUpdateDtoToEmployee(EmployeeUpdateDto employeeUpdateDto);

  Employee employeeCreateDtoToEmployee(EmployeeCreateDto employeeCreateDto);

  Brand brandDtoToBrand(BrandDto brandDto);

  BrandDto brandToBrandDto(Brand brand);

  @Mapping(source = "brand", target = "brandDto")
  @Mapping(target = "phoneDto", ignore = true)
  ProductDto productToProductDto(Product product);

  Category categoryDtoToCategory(CategoryDto categoryDto);

  CategoryDto categoryToCategoryDto(Category category);

  @Mapping(source = "brandDto", target = "brand")
  @Mapping(source = "phoneDto", target = "phone")
  Product productDtoToProduct(ProductDto productDto);

  @Mapping(source = "product", target = "productDto")
  PhoneDto phoneToPhoneDto(Phone phone);

  @Mapping(source = "productDto", target = "product")
  Phone phoneDtoToPhone(PhoneDto phoneDto);
}
