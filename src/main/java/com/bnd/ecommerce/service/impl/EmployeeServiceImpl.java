package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.repository.EmployeeRepository;
import com.bnd.ecommerce.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee findByEmail(String email) {
    return employeeRepository.findByEmail(email);
  }

  @Override
  public Employee save(Employee employee) {
    return employeeRepository.save(employee);
  }
}
