package com.bnd.ecommerce.service;

import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Phone;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PhoneService {

  Phone update(PhoneDto phoneDto, MultipartFile multipartFile);

  Phone save(PhoneDto phoneDto);

  List<Phone> listPhones();

  //  Phone updateWithNotImage(PhoneDto phoneDto);

  Phone create(PhoneDto phoneDto, MultipartFile multipartFile, MultipartFile[] imagesDetail);

  Page<Phone> phonePage(int numPage, String sortField, String sortDir, int size, String keyword);
}
