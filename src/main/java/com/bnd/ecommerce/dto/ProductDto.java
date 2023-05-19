package com.bnd.ecommerce.dto;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.bnd.ecommerce.entity.CreateUpdateTimeStamp;
import org.hibernate.validator.constraints.Length;

public class ProductDto extends CreateUpdateTimeStamp {
  private long id;

  @Column(nullable = false, unique = true, length = 512)
  @NotBlank(message = "Product name cannot blank")
  @Length(min = 5, max = 512, message = "Product name must be between 5-512 characters")
  private String name;

  private String description;

  @Min(value = 10, message = "Product price must be greater or equal to 10")
  @Max(value = 50000, message = "Product price must be less than or equal to 50000")
  private float price;

  private BrandDto brandDto;


  private CategoryDto categoryDto;


  public CategoryDto getCategoryDto() {
    return categoryDto;
  }

  public void setCategoryDto(CategoryDto categoryDto) {
    this.categoryDto = categoryDto;
  }

  private PhoneDto phoneDto;


  public PhoneDto getPhoneDto() {
    return phoneDto;
  }

  public void setPhoneDto(PhoneDto phoneDto) {
    this.phoneDto = phoneDto;
  }



  private Set<ProductLogDto> productLogDtoSet;


  private Set<ProductDetailImageDto> productDetailImageDtoSet;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public BrandDto getBrandDto() {
    return brandDto;
  }

  public void setBrandDto(BrandDto brandDto) {
    this.brandDto = brandDto;
  }

  public Set<ProductLogDto> getProductLogDtoSet() {
    return productLogDtoSet;
  }

  public void setProductLogDtoSet(Set<ProductLogDto> productLogDtoSet) {
    this.productLogDtoSet = productLogDtoSet;
  }


  public Set<ProductDetailImageDto> getProductDetailImageDtoSet() {
    return productDetailImageDtoSet;
  }

  public void setProductDetailImageDtoSet(Set<ProductDetailImageDto> productDetailImageDtoSet) {
    this.productDetailImageDtoSet = productDetailImageDtoSet;
  }
}
