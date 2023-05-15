package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.EmployeeCreateDto;
import com.bnd.ecommerce.dto.EmployeeUpdateDto;
import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.entity.employee.EmployeeLog;
import com.bnd.ecommerce.enums.LogTypeEmployee;
import com.bnd.ecommerce.exception.NotFoundException;
import com.bnd.ecommerce.mapper.MapStructMapper;
import com.bnd.ecommerce.repository.EmployeeRepository;
import com.bnd.ecommerce.service.EmployeeService;
import com.bnd.ecommerce.service.EmployeeServiceLog;
import com.bnd.ecommerce.service.RoleService;
import java.util.Optional;
import javax.persistence.EntityManager;
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

  private final MapStructMapper mapStructMapper;

  private final EmployeeRepository employeeRepository;

  private final EmployeeServiceLog employeeServiceLog;

  private final RoleService roleService;

  public EmployeeServiceImpl(
      MapStructMapper mapStructMapper,
      EmployeeRepository employeeRepository,
      EmployeeServiceLog employeeServiceLog,
      RoleService roleService,
      EntityManager entityManager) {
    this.mapStructMapper = mapStructMapper;
    this.employeeRepository = employeeRepository;
    this.employeeServiceLog = employeeServiceLog;
    this.roleService = roleService;
  }

  @Override
  public Employee findByEmail(String email) {
    return employeeRepository.findByEmail(email);
  }

  @Override
  @Transactional
  public Employee save(
      Employee employee,
      Authentication authentication,
      String action,
      LogTypeEmployee logTypeEmployee) {
    employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
    Employee savedEmployee = employeeRepository.save(employee);
    EmployeeLog employeeLog = new EmployeeLog();
    employeeLog.setLogTypeEmployee(logTypeEmployee);
    StringBuilder stringBuilder = new StringBuilder(savedEmployee.toString() + " was created by ");
    if (authentication == null) {
      stringBuilder.append("DEFAULT" + " ");
    } else {
      stringBuilder.append(authentication.getName() + " ");
      stringBuilder.append(authentication.getAuthorities().toString());
    }
    employeeLog.setMessageLog(stringBuilder.toString());
    employeeServiceLog.save(employeeLog);
    return savedEmployee;
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

  @Override
  public Employee findById(Long id) {
    Optional<Employee> foundEmployee = employeeRepository.findById(id);
    if (foundEmployee.isPresent()) {
      return foundEmployee.get();
    } else {
      throw new NotFoundException("Employee with id wasn't found");
    }
  }

  @Transactional
  @Override
  public Employee updateNoPassword(
      long id, EmployeeUpdateDto employeeUpdateDto, Authentication authentication) {
    Employee savedEmployee = mapStructMapper.employeeUpdateDtoToEmployee(employeeUpdateDto);
    savedEmployee.setId(id);
    savedEmployee.setEmail(employeeRepository.findEmailById(id));
    EmployeeLog employeeLog = new EmployeeLog();
    employeeLog.setLogTypeEmployee(LogTypeEmployee.UPDATED);
    StringBuilder stringBuilder = new StringBuilder(savedEmployee.toString() + " was updated by ");
    stringBuilder.append(authentication.getName() + " ");
    stringBuilder.append(authentication.getAuthorities().toString());
    employeeLog.setMessageLog(stringBuilder.toString());
    employeeServiceLog.save(employeeLog);
    return employeeRepository.save(savedEmployee);
  }

  @Override
  public EmployeeUpdateDto getEmployeePostDtoById(long id) {
    Employee employee = findById(id);
    return mapStructMapper.employeeToEmployeePostDto(employee);
  }

  @Transactional
  @Override
  public boolean deleteById(long id, Authentication authentication) {

    Employee deletedEmployee = findById(id);
    employeeRepository.deleteEmployeeRoleByEmployeeId(id);

    EmployeeLog employeeLog = new EmployeeLog();
    employeeLog.setLogTypeEmployee(LogTypeEmployee.DELETED);
    StringBuilder stringBuilder = new StringBuilder(deletedEmployee.toString() + " was delete by ");
    stringBuilder.append(authentication.getName() + " ");
    stringBuilder.append(authentication.getAuthorities().toString());
    employeeLog.setMessageLog(stringBuilder.toString());
    employeeServiceLog.save(employeeLog);
    deletedEmployee.getRoles().clear();

    employeeRepository.deleteById(id);
    boolean exist = employeeRepository.existsById(id);
    return !exist;
  }

  @Override
  public Employee createNewEmployee(
      EmployeeCreateDto employeeCreateDto, Authentication authentication) {
    Employee employee = mapStructMapper.employeeCreateDtoToEmployee(employeeCreateDto);
    Employee savedEmployee = save(employee, authentication, "Create", LogTypeEmployee.CREATED);
    if (savedEmployee != null) return employee;
    else {
      throw new RuntimeException("Create new Employee failed");
    }
  }
}
