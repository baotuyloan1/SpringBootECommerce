package com.bnd.ecommerce.repository;

import com.bnd.ecommerce.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("SELECT ph FROM Phone ph INNER JOIN Product pr ON ph.product.id = pr.id INNER JOIN Brand br ON pr.brand.id = br.id INNER JOIN Category ca WHERE CONCAT(ph.ram,' ',ph.chip,' ', pr.name, ' ', pr.description,' ' , br.name, ' ',ca.name) LIKE %?1%")
    public void searchPhone(String keyword);
}
