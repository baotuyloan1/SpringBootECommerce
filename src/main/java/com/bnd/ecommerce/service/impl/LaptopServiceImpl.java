package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.LaptopDto;
import com.bnd.ecommerce.entity.Laptop;
import com.bnd.ecommerce.repository.LaptopRepository;
import com.bnd.ecommerce.service.LaptopService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LaptopServiceImpl implements LaptopService {
  private final LaptopRepository laptopRepository;

  public LaptopServiceImpl(LaptopRepository laptopRepository) {
    this.laptopRepository = laptopRepository;
  }

  @Override
  public Laptop createLaptop(LaptopDto laptop) {
    return null;
  }

  @Override
  public List<Laptop> listLaptops() {
    return laptopRepository.findAll();
  }

  @Override
  public Page<Laptop> listAll(int numPage, String sortField, String sortDir, int size) {
    PageRequest pageRequest =
        PageRequest.of(
            numPage - 1,
            size,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    return laptopRepository.findAll(pageRequest);
  }
}
