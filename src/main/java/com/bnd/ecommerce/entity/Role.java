package com.bnd.ecommerce.entity;

import com.bnd.ecommerce.entity.employee.EmployeeRole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;


    @OneToMany(mappedBy = "role")
    private Set<EmployeeRole> employeeRoles;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String keyword) {
        this.description = keyword;
    }

    public Set<EmployeeRole> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(Set<EmployeeRole> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }
}
