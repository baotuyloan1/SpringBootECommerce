package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.ImageDetailDto;
import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.mapper.MapStructMapper;
import com.bnd.ecommerce.repository.ProductRepository;
import com.bnd.ecommerce.service.ProductService;
import com.bnd.ecommerce.util.FileUtil;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  private final MapStructMapper mapStructMapper;

  public ProductServiceImpl(ProductRepository productRepository, MapStructMapper mapStructMapper) {
    this.productRepository = productRepository;
    this.mapStructMapper = mapStructMapper;
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
}
