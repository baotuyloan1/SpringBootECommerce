package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Role;
import org.springframework.data.domain.Page;

public interface RoleService {

    Role save(Role role);

    Page<Role> listAll(int pageNum, String sortField, String sortDir, int size);
}
