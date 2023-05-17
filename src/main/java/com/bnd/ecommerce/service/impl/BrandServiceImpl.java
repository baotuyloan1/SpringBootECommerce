package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.BrandDto;
import com.bnd.ecommerce.entity.Brand;
import com.bnd.ecommerce.exception.DeleteFailException;
import com.bnd.ecommerce.exception.NotFoundException;
import com.bnd.ecommerce.mapper.MapStructMapper;
import com.bnd.ecommerce.repository.BrandRepository;
import com.bnd.ecommerce.service.BrandService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;

  private final MapStructMapper mapStructMapper;

  public BrandServiceImpl(BrandRepository brandRepository, MapStructMapper mapStructMapper) {
    this.brandRepository = brandRepository;
    this.mapStructMapper = mapStructMapper;
  }

  @Override
  public List<Brand> listBrands() {
    return brandRepository.findAll();
  }

  @Override
  public Brand saveBrand(BrandDto brandDto) {
    Brand brand = mapStructMapper.brandDtoToBrand(brandDto);
    if (brand.getId() > 0) {
      Optional<Brand> foundBrand = brandRepository.findById(brand.getId());
      if (foundBrand.isPresent()) {
        brand.setCreateTime(foundBrand.get().getCreateTime());
      } else {
        throw new NotFoundException("Brand not found to update");
      }
    }
    return brandRepository.save(brand);
  }

  @Override
  public Page<Brand> pageBrands(int numPage, String sortDir, String sortField, int size) {
    Pageable pageable =
        PageRequest.of(
            numPage - 1,
            size,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    return brandRepository.findAll(pageable);
  }

  @Override
  public Brand findById(int id) {
    Optional<Brand> foundBrand = brandRepository.findById(id);
    if (foundBrand.isPresent()) {
      return foundBrand.get();
    } else throw new NotFoundException("Can't find Brand with " + id);
  }

  @Override
  public boolean deleteById(int id) {
    brandRepository.deleteById(id);
    if (brandRepository.existsById(id)) {
      throw new DeleteFailException("Delete brand fail");
    } else {
      return true;
    }
  }
}
