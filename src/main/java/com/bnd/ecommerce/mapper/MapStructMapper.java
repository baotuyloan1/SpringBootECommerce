package com.bnd.ecommerce.mapper;

import com.bnd.ecommerce.dto.*;
import com.bnd.ecommerce.entity.*;
import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.entity.stock.Stock;
import com.bnd.ecommerce.entity.stock.Warehouse;
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
  @Mapping(target = "imageDetailDtoSet", source = "imageDetailSet")
  ProductDto productToProductDto(Product product);

  Category categoryDtoToCategory(CategoryDto categoryDto);

  CategoryDto categoryToCategoryDto(Category category);

  @Mapping(source = "brandDto", target = "brand")
  @Mapping(source = "phoneDto", target = "phone")
  @Mapping(source = "imageDetailDtoSet", target = "imageDetailSet")
  Product productDtoToProduct(ProductDto productDto);

  @Mapping(source = "product", target = "productDto")
  PhoneDto phoneToPhoneDto(Phone phone);

  @Mapping(source = "productDto", target = "product")
  Phone phoneDtoToPhone(PhoneDto phoneDto);

  ImageDetail imageDetailDtoToImageDetail(ImageDetailDto imageDetailDto);

  ImageDetailDto imageDetailToImageDetailDto(ImageDetail imageDetail);


  Stock stockDtoToStock(StockDto stockDto);


  StockDto stockToStockDto(Stock stock);

  Warehouse wareHouseDtoToWareHouse(WarehouseDto warehouseDto);

  WarehouseDto wareHouseToWareHouseDto(Warehouse warehouse);
  @Mapping(source = "product", target = "productDto")
  LaptopDto laptopToLaptopDto(Laptop laptop);

  @Mapping(source = "productDto", target = "product")
  Laptop laptopDtoToLaptop(LaptopDto laptopDto);
}
