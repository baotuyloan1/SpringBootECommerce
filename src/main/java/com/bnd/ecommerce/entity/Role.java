package com.bnd.ecommerce.entity;

import com.bnd.ecommerce.entity.employee.Employee;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String keyword;


    @OneToMany(mappedBy = "role")
    private Set<Employee> employee;



}
