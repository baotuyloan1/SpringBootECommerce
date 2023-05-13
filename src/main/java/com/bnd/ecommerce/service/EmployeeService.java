package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface EmployeeService {

  Employee findByEmail(String email);

  Employee save(Employee employee, Authentication authentication);

  Page<Employee> listAll(int size, int numPage, String sortField, String sortDir);
}
