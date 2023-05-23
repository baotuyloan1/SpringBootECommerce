package com.bnd.ecommerce.service;

import com.bnd.ecommerce.dto.LaptopDto;
import com.bnd.ecommerce.entity.Laptop;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LaptopService {

    Laptop createLaptop(LaptopDto laptopDto);
    List<Laptop> listLaptops();

    Page<Laptop> listAll(int numPage, String sortField, String sortDir, int size);
}
