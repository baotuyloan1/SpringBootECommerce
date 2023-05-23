package com.bnd.ecommerce.repository;

import com.bnd.ecommerce.entity.stock.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<Warehouse, Integer> {}
