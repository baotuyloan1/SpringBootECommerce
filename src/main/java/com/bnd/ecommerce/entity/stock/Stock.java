package com.bnd.ecommerce.entity.stock;

import com.bnd.ecommerce.entity.CreateUpdateTimeStamp;
import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.entity.employee.Employee;
import javax.persistence.*;

@Entity
@Table(name = "stock")
public class Stock extends CreateUpdateTimeStamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long stockId;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  private Warehouse warehouse;

  private int quantityInStock;

  private boolean enable;

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  public long getStockId() {
    return stockId;
  }

  public void setStockId(long stockId) {
    this.stockId = stockId;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  public int getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(int quantityInStock) {
    this.quantityInStock = quantityInStock;
  }
}
