package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.ImageDetailDto;
import com.bnd.ecommerce.dto.LaptopDto;
import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.mapper.MapStructMapper;
import com.bnd.ecommerce.repository.PhoneRepository;
import com.bnd.ecommerce.repository.ProductRepository;
import com.bnd.ecommerce.service.CategoryService;
import com.bnd.ecommerce.service.ProductService;
import com.bnd.ecommerce.util.FileUtil;
import java.util.*;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  private final MapStructMapper mapStructMapper;

  private final CategoryService categoryService;

  public ProductServiceImpl(
      ProductRepository productRepository,
      MapStructMapper mapStructMapper,
      PhoneRepository phoneRepository,
      CategoryService categoryService1) {
    this.productRepository = productRepository;
    this.mapStructMapper = mapStructMapper;
    this.categoryService = categoryService1;
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
  public Page<Product> listAll(
      int numPage, String sortField, String sortDir, int size, String keyword) {
    Pageable pageable =
        PageRequest.of(
            numPage - 1,
            size,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    if (keyword != null && !keyword.trim().equals("")) {
      return productRepository.search(keyword, pageable);
    }

    return productRepository.findAll(pageable);
  }

  //  @Override
  //  public ProductDto findById(long id) {
  //    Optional<Product> foundProduct = productRepository.findById(id);
  //    if (foundProduct.isPresent()) {
  //      return mapStructMapper.productToProductDto(foundProduct.get());
  //    } else {
  //      throw new NotFoundException("Product not found");
  //    }
  //  }

  @Transactional
  @Override
  public Object findById(long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isPresent()) {
      Product foundProduct = optionalProduct.get();
      if (foundProduct.getPhone() != null) {
        PhoneDto phoneDto = mapStructMapper.phoneToPhoneDto(foundProduct.getPhone());
        phoneDto
            .getProductDto()
            .setCategoryDto(
                mapStructMapper.categoryToCategoryDto(
                    getMainCategory(foundProduct.getCategories())));
        Set<ImageDetailDto> imageDetailDtoSet = phoneDto.getProductDto().getImageDetailDtoSet();
        for (ImageDetailDto imageDetailDto : imageDetailDtoSet) {
          imageDetailDto.setProductDtoId(id);
        }
        return phoneDto;
      }
      if (foundProduct.getLaptop() != null) {
        LaptopDto laptopDto = mapStructMapper.laptopToLaptopDto(foundProduct.getLaptop());
        laptopDto
            .getProductDto()
            .setCategoryDto(
                mapStructMapper.categoryToCategoryDto(
                    getMainCategory(foundProduct.getCategories())));
        Set<ImageDetailDto> imageDetailDtoSet = laptopDto.getProductDto().getImageDetailDtoSet();
        for (ImageDetailDto imageDetailDto : imageDetailDtoSet) {
          imageDetailDto.setProductDtoId(id);
        }
        return laptopDto;
      }
    }
    return null;
  }

  @Transactional
  @Override
  public boolean deleteProductById(long id) {
    FileUtil.deleteImageDirByProductId(id);
    productRepository.deleteProductCategoryByProductId(id);
    productRepository.deleteById(id);
    return !productRepository.existsById(id);
  }

  private Category getMainCategory(Set<Category> categorySet) {
    for (Category category : categorySet) {
      if (category.getChildren().isEmpty()) {
        return category;
      }
    }
    return null;
  }

  @Override
  public Category findRootCategory(Category category, Set<Category> categories) {
    Category currentCategory = categoryService.findById(category.getId());
    categories.add(currentCategory);
    Category parentCategory = (Category) Hibernate.unproxy(currentCategory.getParentCategory());
    if (parentCategory != null) {
      return findRootCategory(parentCategory, categories);
    } else return category;
  }

  @Override
  public Product findByName(String name) {
    return productRepository.findByName(name);
  }
}
