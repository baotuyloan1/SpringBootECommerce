package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.Role;
import java.util.List;

public interface RoleService {

    Role save(Role role);

    List<Role> listAll();

    Role findById(int id);
}
