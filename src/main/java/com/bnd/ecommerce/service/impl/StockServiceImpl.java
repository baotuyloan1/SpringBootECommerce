package com.bnd.ecommerce.service.impl;

import com.bnd.ecommerce.dto.StockDto;
import com.bnd.ecommerce.entity.stock.Stock;
import com.bnd.ecommerce.exception.CreateFailException;
import com.bnd.ecommerce.exception.NotFoundException;
import com.bnd.ecommerce.mapper.MapStructMapper;
import com.bnd.ecommerce.repository.StockRepository;
import com.bnd.ecommerce.service.StockService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

  private final StockRepository stockRepository;

  private final MapStructMapper mapStructMapper;

  public StockServiceImpl(StockRepository stockRepository, MapStructMapper mapStructMapper) {
    this.stockRepository = stockRepository;
    this.mapStructMapper = mapStructMapper;
  }

  @Override
  public Page<Stock> stockPage(
      int pageNum, String sortField, String sortDir, int size, String keyword) {
    Pageable pageable =
        PageRequest.of(
            pageNum - 1,
            size,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    return stockRepository.findAll(pageable);
  }

  @Override
  public StockDto findStockDtoById(long id) {
    Optional<Stock> stock = stockRepository.findById(id);
    if (stock.isPresent()) {
      StockDto stockDto = mapStructMapper.stockToStockDto(stock.get());
      return stockDto;
    } else throw new NotFoundException("Stock not found");
  }

  @Override
  public boolean deleteById(long id) {
    stockRepository.deleteById(id);
    return !stockRepository.existsById(id);
  }

  @Override
  public Stock save(StockDto stockDto) {
    Stock stock = mapStructMapper.stockDtoToStock(stockDto);
    if (stock != null) return stock;
    else throw new CreateFailException("Create fail");
  }
}
