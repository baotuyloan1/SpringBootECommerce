package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.repository.PhoneRepository;
import com.bnd.ecommerce.service.PhoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {
    private PhoneRepository phoneRepository;
    public PhoneServiceImpl(PhoneRepository phoneRepository){
        this.phoneRepository = phoneRepository;
    };
    @Override
    public Phone savePhone(Phone phone) {
        return null;
    }

    @Override
    public List<Phone> listPhones() {
        return phoneRepository.findAll();
    }
}
