package com.bnd.ecommerce.dto;

import com.bnd.ecommerce.entity.Role;
import com.bnd.ecommerce.validator.email.UniqueEmail;

import javax.persistence.Column;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class EmployeeModel {
    private long id;

    private String firstName;
    private String lastName;

    private boolean isEnabled;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }


    @NotBlank
    @Email
    @UniqueEmail(message = "Entity: Email exits in system")
    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

  private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoleModels() {
        return roles;
    }

    public void setRoleModels(Set<Role> roles) {
        this.roles = roles;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
