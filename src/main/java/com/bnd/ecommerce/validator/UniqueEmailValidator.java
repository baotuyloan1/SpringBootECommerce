package com.bnd.ecommerce.validator;

import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.repository.EmployeeRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  @Autowired private EmployeeRepository employeeRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Employee employee = employeeRepository.findByEmail(value);
    if (employee != null) {
      context
          .buildConstraintViolationWithTemplate(
              context.getDefaultConstraintMessageTemplate() // get message
              )
          .addConstraintViolation() // thêm lỗi vào context validation hiện tại
      ;
      return false;
    }
    return true;
  }
}
