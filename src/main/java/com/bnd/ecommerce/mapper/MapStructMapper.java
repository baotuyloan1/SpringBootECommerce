package com.bnd.ecommerce.mapper;

import com.bnd.ecommerce.dto.BrandDto;
import com.bnd.ecommerce.dto.EmployeeCreateDto;
import com.bnd.ecommerce.dto.EmployeeUpdateDto;
import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Brand;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.entity.employee.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

  EmployeeUpdateDto employeeToEmployeePostDto(Employee employee);

  Employee employeeUpdateDtoToEmployee(EmployeeUpdateDto employeeUpdateDto);

  Employee employeeCreateDtoToEmployee(EmployeeCreateDto employeeCreateDto);

  Brand brandDtoToBrand(BrandDto brandDto);

  BrandDto brandToBrandDto(Brand brand);

  PhoneDto phoneToPhoneDto()
}
