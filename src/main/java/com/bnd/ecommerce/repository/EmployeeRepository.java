package com.bnd.ecommerce.repository;

import com.bnd.ecommerce.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Query("SELECT e FROM Employee e WHERE  e.email = :email")
  Employee getEmployeeByEmail(@Param("email") String email);

  Employee findByEmail(String email);
}
