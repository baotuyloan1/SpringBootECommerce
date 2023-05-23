package com.bnd.ecommerce.service;

import com.bnd.ecommerce.dto.WarehouseDto;
import com.bnd.ecommerce.entity.stock.Warehouse;
import org.springframework.data.domain.Page;

public interface WareHouseService {

    Warehouse save(WarehouseDto warehouseDto);

    Page<Warehouse> pageWareHouse(int pageNum,String sortField,String sortDir,int size,String keyword);

    WarehouseDto findWarehouseDtoById(int id);

    boolean deleteById(int id);
}
