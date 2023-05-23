package com.bnd.ecommerce.dto;

import com.bnd.ecommerce.entity.CreateUpdateTimeStamp;

public class StockDto extends CreateUpdateTimeStamp {

  private long stockId;

  private ProductDto productDto;

  private WarehouseDto warehouseDto;

  private int quantityInStock;

  private boolean status;

  public long getStockId() {
    return stockId;
  }

  public void setStockId(long stockId) {
    this.stockId = stockId;
  }

  public ProductDto getProductDto() {
    return productDto;
  }

  public void setProductDto(ProductDto productDto) {
    this.productDto = productDto;
  }

  public WarehouseDto getWarehouseDto() {
    return warehouseDto;
  }

  public void setWarehouseDto(WarehouseDto warehouseDto) {
    this.warehouseDto = warehouseDto;
  }

  public int getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(int quantityInStock) {
    this.quantityInStock = quantityInStock;
  }
}
