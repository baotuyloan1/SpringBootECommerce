package com.bnd.ecommerce.security;

import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.entity.employee.EmployeeRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EmployeeDetails implements UserDetails {

  private final Employee employee;

  public EmployeeDetails(Employee employee) {
    this.employee = employee;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<EmployeeRole> employeeRoles = employee.getEmployeeRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (EmployeeRole role : employeeRoles) {
      authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return employee.getPassword();
  }

  @Override
  public String getUsername() {
    return employee.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
