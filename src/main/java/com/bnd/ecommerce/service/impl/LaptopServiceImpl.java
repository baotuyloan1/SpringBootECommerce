package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.Laptop;
import com.bnd.ecommerce.repository.LaptopRepository;
import com.bnd.ecommerce.service.LaptopService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LaptopServiceImpl implements LaptopService {
    private LaptopRepository laptopRepository;
    public LaptopServiceImpl(LaptopRepository laptopRepository){
        this.laptopRepository = laptopRepository;
    }
    @Override
    public Laptop saveLaptop(Laptop laptop) {
        return null;
    }

    @Override
    public List<Laptop> listLaptops() {
        return laptopRepository.findAll();
    }
}
