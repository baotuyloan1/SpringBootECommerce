package com.bnd.ecommerce.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
public class Product extends CreateUpdateTimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 512)
  @NotBlank(message = "Product name cannot blank")
  @Length(min = 5, max = 512, message = "Product name must be between 5-512 characters")
  private String name;

  private String description;

  @Min(value = 10, message = "Product price must be greater or equal to 10")
  @Max(value = 50000, message = "Product price must be less than or equal to 50000")
  private float price;

  @ManyToOne private Brand brand;

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  @OneToMany(mappedBy = "product")
  private Set<ProductLog> productLog;

  @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public Phone phone;

//  @OneToOne(fetch = FetchType.EAGER)
//  public Laptop laptop;
//
//  @OneToOne
//  public Tablet tablet;


  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "product_category",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories;

  public Phone getPhone() {
    return phone;
  }

  public void setPhone(Phone phone) {
    this.phone = phone;
  }

  @OneToMany(mappedBy = "product")
  private Set<ProductDetailImage> productDetailImages;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public Set<ProductLog> getProductLog() {
    return productLog;
  }

  public void setProductLog(Set<ProductLog> productLog) {
    this.productLog = productLog;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public Set<ProductDetailImage> getProductDetailImages() {
    return productDetailImages;
  }

  public void setProductDetailImages(Set<ProductDetailImage> productDetailImages) {
    this.productDetailImages = productDetailImages;
  }

  public void addCategory(Category category) {
    if (categories == null) categories = new HashSet<>();
    this.categories.add(category);
  }

}
