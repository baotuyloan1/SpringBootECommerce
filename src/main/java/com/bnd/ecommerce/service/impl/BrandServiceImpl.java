package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.Brand;
import com.bnd.ecommerce.repository.BrandRepository;
import com.bnd.ecommerce.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> listBrands() {
        return brandRepository.findAll();
    }
}
