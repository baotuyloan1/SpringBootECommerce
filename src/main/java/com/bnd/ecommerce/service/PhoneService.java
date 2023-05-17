package com.bnd.ecommerce.service;

import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Phone;

import java.util.List;

public interface PhoneService {

    Phone save(PhoneDto phone);

    List<Phone> listPhones();
}
