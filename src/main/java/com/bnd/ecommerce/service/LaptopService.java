package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Laptop;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LaptopService {

    Laptop saveLaptop(Laptop laptop);
    List<Laptop> listLaptops();

    Page<Laptop> listAll(int numPage, String sortField, String sortDir, int size);
}
