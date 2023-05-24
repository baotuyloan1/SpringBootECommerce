package com.bnd.ecommerce.entity;

import com.bnd.ecommerce.entity.stock.Stock;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.bnd.ecommerce.validator.email.UniqueEmail;
import com.bnd.ecommerce.validator.product.UniqueProductName;
import org.hibernate.validator.constraints.Length;

@Entity
public class Product extends CreateUpdateTimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 512)
  @NotBlank(message = "Product name cannot blank")
  @Length(min = 5, max = 512, message = "Product name must be between 5-512 characters")
  @UniqueProductName (message = "Exist product in database")
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

  @OneToOne(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
  public Laptop laptop;

  @OneToOne public Tablet tablet;

  @OneToMany public Set<Stock> stockSet = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "product_category",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories;

  private String image;

  public Tablet getTablet() {
    return tablet;
  }

  public void setTablet(Tablet tablet) {
    this.tablet = tablet;
  }

  public Set<Stock> getStockSet() {
    return stockSet;
  }

  public void setStockSet(Set<Stock> stockSet) {
    this.stockSet = stockSet;
  }

  public Laptop getLaptop() {
    return laptop;
  }

  public void setLaptop(Laptop laptop) {
    this.laptop = laptop;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void addImageDetail(ImageDetail imageDetail) {
    if (imageDetailSet == null) imageDetailSet = new HashSet<>();
    imageDetailSet.add(imageDetail);
  }

  @Transient
  public String getPhotoImagePath() {
    if (image == null || id == 0) return null;
    return "/phone-photos/" + id + "/" + image;
  }

  public Phone getPhone() {
    return phone;
  }

  public void setPhone(Phone phone) {
    this.phone = phone;
  }

  @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<ImageDetail> imageDetailSet;

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

  public Set<ImageDetail> getImageDetailSet() {
    return imageDetailSet;
  }

  public void setImageDetailSet(Set<ImageDetail> imageDetails) {
    this.imageDetailSet = imageDetails;
  }

  public void addCategory(Category category) {
    if (categories == null) categories = new HashSet<>();
    this.categories.add(category);
  }
}
