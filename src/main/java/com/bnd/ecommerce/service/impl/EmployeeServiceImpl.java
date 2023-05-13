package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.entity.employee.EmployeeLog;
import com.bnd.ecommerce.entity.employee.EmployeeRole;
import com.bnd.ecommerce.enums.LogTypeEmployee;
import com.bnd.ecommerce.repository.EmployeeRepository;
import com.bnd.ecommerce.service.EmployeeService;
import com.bnd.ecommerce.service.EmployeeServiceLog;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;

  private final EmployeeServiceLog employeeServiceLog;

  public EmployeeServiceImpl(
      EmployeeRepository employeeRepository, EmployeeServiceLog employeeServiceLog) {
    this.employeeRepository = employeeRepository;
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
    employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
    EmployeeLog employeeLog = new EmployeeLog();
    employeeLog.setEmployee(employee);
    employeeLog.setLogTypeEmployee(LogTypeEmployee.CREATED);
    StringBuilder stringBuilder = new StringBuilder("Employee was created by ");
    stringBuilder.append(authentication.getName() + " ");
    stringBuilder.append(authentication.getAuthorities().toString());
    employeeLog.setMessageLog(stringBuilder.toString());

    employeeServiceLog.save(employeeLog);
    return employeeRepository.save(employee);
  }

  @Override
  public Page<Employee> listAll(int size, int numPage, String sortField, String sortDir) {
    Pageable pageable =
        PageRequest.of(
            numPage - 1,
            size,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    return employeeRepository.findAll(pageable);
  }
}
