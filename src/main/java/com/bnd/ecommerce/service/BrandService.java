package com.bnd.ecommerce.service;

import com.bnd.ecommerce.dto.BrandDto;
import com.bnd.ecommerce.entity.Brand;
import java.util.List;
import org.springframework.data.domain.Page;

public interface BrandService {

  List<Brand> listBrands();

  Brand saveBrand(BrandDto brand);

  Page<Brand> pageBrands(int numPage, String sortDir, String sortField, int size);

  Brand findById(int id);

  boolean deleteById(int id);
}
