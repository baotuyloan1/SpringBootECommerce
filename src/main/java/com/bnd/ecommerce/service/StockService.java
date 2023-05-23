package com.bnd.ecommerce.service;

import com.bnd.ecommerce.dto.StockDto;
import com.bnd.ecommerce.entity.stock.Stock;
import org.springframework.data.domain.Page;

public interface StockService {

  Page<Stock> stockPage(
      int pageNum, String sortField, String sortDir, int size, String keyword);

  StockDto findStockDtoById(long id);

  boolean deleteById(long id);

  Stock save(StockDto stockDto);
}
