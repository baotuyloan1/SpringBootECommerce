package com.bnd.ecommerce.service;

import com.bnd.ecommerce.entity.employee.Employee;

public interface EmployeeService {

    Employee findByEmail(String email);

}
