package com.bnd.ecommerce.entity.employee;

import com.bnd.ecommerce.entity.CreateTimestamp;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.bnd.ecommerce.validator.UniqueEmail;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "employee", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Employee extends CreateTimestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<EmployeeRole> employeeRoles;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public Set<EmployeeRole> getEmployeeRoles() {
    return employeeRoles;
  }

  public void setEmployeeRoles(Set<EmployeeRole> employeeRoles) {
    this.employeeRoles = employeeRoles;
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


}
