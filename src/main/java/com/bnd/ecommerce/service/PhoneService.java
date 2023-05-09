package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Phone;

import java.util.List;

public interface PhoneService {

    Phone savePhone(Phone phone);

    List<Phone> listPhones();
}
