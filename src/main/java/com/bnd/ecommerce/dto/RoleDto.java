package com.bnd.ecommerce.dto;

import com.bnd.ecommerce.entity.employee.EmployeeRole;
import com.bnd.ecommerce.validator.role.UniqueRoleName;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class RoleModel {

    @NotBlank
    @JsonProperty("id")
    private int id;

    @NotBlank
    @UniqueRoleName(message = "Message: name của role không được trùng")
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("description")
    private String description;


    @JsonProperty("employees")
    private Set<EmployeeRole> employeeRoles;
}
