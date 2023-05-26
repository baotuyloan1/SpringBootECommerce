package com.bnd.ecommerce.dto;

import com.bnd.ecommerce.entity.Laptop;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.entity.Tablet;

import java.util.List;

public class ProductRestAPI {

    private String name;
    private String description;
    private double price;

    private String brand;

    private Phone phone;
    private Laptop laptop;
    private Tablet tablet;

    private List<String> categoryName;

}
