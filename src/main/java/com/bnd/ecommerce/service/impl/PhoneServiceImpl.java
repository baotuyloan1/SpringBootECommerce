package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.entity.ImageDetail;
import com.bnd.ecommerce.mapper.MapStructMapper;
import com.bnd.ecommerce.repository.PhoneRepository;
import com.bnd.ecommerce.service.CategoryService;
import com.bnd.ecommerce.service.ImageDetailService;
import com.bnd.ecommerce.service.PhoneService;
import com.bnd.ecommerce.service.ProductService;
import com.bnd.ecommerce.util.FileUtil;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhoneServiceImpl implements PhoneService {
  private final PhoneRepository phoneRepository;

  private final MapStructMapper mapStructMapper;

  private final ProductService productService;
  private final CategoryService categoryService;

  private final ImageDetailService imageDetailService;


  private static final String ROOT_DIR = "phone-photos/";

  public PhoneServiceImpl(
          PhoneRepository phoneRepository,
          MapStructMapper mapStructMapper,
          ProductService productService, CategoryService categoryService,
          ImageDetailService imageDetailService) {
    this.phoneRepository = phoneRepository;
    this.mapStructMapper = mapStructMapper;
    this.productService = productService;
    this.categoryService = categoryService;
    this.imageDetailService = imageDetailService;
  }

  @Override
  @Transactional
  public Phone create(PhoneDto phoneDto, MultipartFile mainImage, MultipartFile[] imagesDetail) {
    String fileName =
        StringUtils.cleanPath(Objects.requireNonNull(mainImage.getOriginalFilename()));
    Phone phone = mapStructMapper.phoneDtoToPhone(phoneDto);
    phone.getProduct().setImage(fileName);
    Category category =
        mapStructMapper.categoryDtoToCategory(phoneDto.getProductDto().getCategoryDto());
    Set<Category> categories = new HashSet<>();
    productService.findRootCategory(category, categories);
    phone.getProduct().setCategories(categories);
    Phone savedPhone = phoneRepository.save(phone);
    String uploadDir = ROOT_DIR + savedPhone.getProduct().getId();
    try {
      FileUtil.saveFile(uploadDir, fileName, mainImage);
      for (MultipartFile imageDetail : imagesDetail) {
        String fileNameDetail =
            StringUtils.cleanPath(Objects.requireNonNull(imageDetail.getOriginalFilename()));
        long size = FileUtil.saveFile(uploadDir, fileNameDetail, imageDetail);
        ImageDetail productDetailImage = new ImageDetail();
        productDetailImage.setDescription("Description of " + fileNameDetail);
        productDetailImage.setProduct(savedPhone.getProduct());
        productDetailImage.setName(fileNameDetail);
        productDetailImage.setSize(size);
        imageDetailService.save(productDetailImage);
      }
    } catch (IOException e) {
      throw new RuntimeException("Save image filed");
    }
    return savedPhone;
  }



  @Transactional
  @Override
  public Phone update(PhoneDto phoneDto, MultipartFile multipartFile) {
    Phone phone = mapStructMapper.phoneDtoToPhone(phoneDto);
    try {
      if (!multipartFile.isEmpty()) {
        String fileName =
            StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = ROOT_DIR + phoneDto.getProductDto().getId();
        FileUtil.deleteFile(uploadDir, phoneDto.getProductDto().getImage());
        uploadDir = ROOT_DIR + phone.getProduct().getId();
        phone.getProduct().setImage(fileName);
        FileUtil.saveFile(uploadDir, fileName, multipartFile);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Category category =
        mapStructMapper.categoryDtoToCategory(phoneDto.getProductDto().getCategoryDto());
    Set<Category> categories = new HashSet<>();
    productService.findRootCategory(category, categories);
    phone.getProduct().setCategories(categories);
    return phoneRepository.save(phone);
  }

  @Transactional
  @Override
  public Phone save(PhoneDto phoneDto) {
    Phone phone = mapStructMapper.phoneDtoToPhone(phoneDto);
    Category category =
        mapStructMapper.categoryDtoToCategory(phoneDto.getProductDto().getCategoryDto());
    Set<Category> categories = new HashSet<>();
    productService.findRootCategory(category, categories);
    phone.getProduct().setCategories(categories);
    return phoneRepository.save(phone);
  }

  @Override
  public List<Phone> listPhones() {
    return phoneRepository.findAll();
  }
}
