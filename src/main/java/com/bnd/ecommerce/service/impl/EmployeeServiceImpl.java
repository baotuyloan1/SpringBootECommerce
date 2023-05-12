package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.entity.employee.EmployeeLog;
import com.bnd.ecommerce.entity.employee.EmployeeRole;
import com.bnd.ecommerce.enums.LogTypeEmployee;
import com.bnd.ecommerce.repository.EmployeeRepository;
import com.bnd.ecommerce.service.EmployeeService;
import java.util.Set;
import javax.transaction.Transactional;

import com.bnd.ecommerce.service.EmployeeServiceLog;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;
//  private final PasswordEncoder passwordEncoder;

  private final EmployeeServiceLog employeeServiceLog;

  public EmployeeServiceImpl(
      EmployeeRepository employeeRepository,
      PasswordEncoder passwordEncoder,
      EmployeeServiceLog employeeServiceLog) {
    this.employeeRepository = employeeRepository;
//    this.passwordEncoder = passwordEncoder;
    this.employeeServiceLog = employeeServiceLog;
  }

  @Override
  public Employee findByEmail(String email) {
    return employeeRepository.findByEmail(email);
  }

  @Override
  @Transactional
  public Employee save(Employee employee, Authentication authentication) {
    Set<EmployeeRole> employeeRoles = employee.getEmployeeRoles();
    for (EmployeeRole employeeRole : employeeRoles) {
      employeeRole.setEmployee(employee);
    }
    employee.setPassword(null);

    EmployeeLog employeeLog = new EmployeeLog();
    employeeLog.setEmployee(employee);
    employeeLog.setLogTypeEmployee(LogTypeEmployee.CREATED);
    employeeLog.setMessageLog("Employee was created by " + authentication.getName());

    employeeServiceLog.save(employeeLog);
    return employeeRepository.save(employee);
  }
}
