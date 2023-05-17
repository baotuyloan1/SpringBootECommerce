package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.repository.PhoneRepository;
import com.bnd.ecommerce.service.PhoneService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PhoneServiceImpl implements PhoneService {
  private PhoneRepository phoneRepository;

  public PhoneServiceImpl(PhoneRepository phoneRepository) {
    this.phoneRepository = phoneRepository;
  }

  @Override
  public Phone save(PhoneDto phone) {
    return phoneRepository.save(phone);
  }

  @Override
  public List<Phone> listPhones() {
    return phoneRepository.findAll();
  }
}
