package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.mapper.MapStructMapper;
import com.bnd.ecommerce.repository.PhoneRepository;
import com.bnd.ecommerce.service.CategoryService;
import com.bnd.ecommerce.service.PhoneService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
public class PhoneServiceImpl implements PhoneService {
  private PhoneRepository phoneRepository;

  private final MapStructMapper mapStructMapper;
  private final CategoryService categoryService;

  public PhoneServiceImpl(
      PhoneRepository phoneRepository,
      MapStructMapper mapStructMapper,
      CategoryService categoryService) {
    this.phoneRepository = phoneRepository;
    this.mapStructMapper = mapStructMapper;
    this.categoryService = categoryService;
  }

  @Transactional
  @Override
  public Phone save(PhoneDto phoneDto) {
    Phone phone = mapStructMapper.phoneDtoToPhone(phoneDto);
    Category category =
        mapStructMapper.categoryDtoToCategory(phoneDto.getProductDto().getCategoryDto());
    Set<Category> deteachedCategories = new HashSet<>();
    Set<Category> mergeCategories = new HashSet<>();
    findRootCategory(category, deteachedCategories);

    for (Category category1 : deteachedCategories) {
      mergeCategories.add(categoryService.saveCategory(category1));
    }

    phone.getProduct().setCategories(mergeCategories);
    return phoneRepository.save(phone);
  }


  public Category findRootCategory(Category category, Set<Category> categories) {
    categories.add(category);
    Category currentCategory = categoryService.findById(category.getId());
    Category parentCategory = (Category) Hibernate.unproxy(currentCategory.getParentCategory());
    if (parentCategory != null) {
      return findRootCategory(parentCategory, categories);
    } else return category;
  }

  @Override
  public List<Phone> listPhones() {
    return phoneRepository.findAll();
  }
}
