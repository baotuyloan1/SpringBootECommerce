package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.employee.Employee;
import org.springframework.security.core.Authentication;

public interface EmployeeService {

  Employee findByEmail(String email);

  Employee save(Employee employee, Authentication authentication);
}
