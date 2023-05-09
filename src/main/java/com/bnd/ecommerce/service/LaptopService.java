package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Laptop;

import java.util.List;

public interface LaptopService {

    Laptop saveLaptop(Laptop laptop);
    List<Laptop> listLaptops();
}
