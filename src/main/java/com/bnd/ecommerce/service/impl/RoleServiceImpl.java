package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.Role;
import com.bnd.ecommerce.repository.RoleRepository;
import com.bnd.ecommerce.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository repository) {
    this.roleRepository = repository;
  }

  @Override
  public Role save(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public Page<Role> listAll(int pageNum, String sortField, String sortDir, int size) {
    Pageable pageable =
        PageRequest.of(
            pageNum,
            size,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    return roleRepository.findAll(pageable);
  }
}
