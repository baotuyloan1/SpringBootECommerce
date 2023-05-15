package com.bnd.ecommerce.mapper;

import com.bnd.ecommerce.dto.EmployeeCreateDto;
import com.bnd.ecommerce.dto.EmployeeUpdateDto;
import com.bnd.ecommerce.entity.employee.Employee;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

  EmployeeUpdateDto employeeToEmployeePostDto(Employee employee);

  Employee employeeUpdateDtoToEmployee(EmployeeUpdateDto employeeUpdateDto);

  Employee employeeCreateDtoToEmployee(EmployeeCreateDto employeeCreateDto);
}
