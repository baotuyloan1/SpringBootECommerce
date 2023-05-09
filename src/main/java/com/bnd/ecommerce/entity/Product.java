package com.bnd.ecommerce.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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

    @ManyToOne
    private Brand brand;


    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @OneToMany(mappedBy = "product")
    private Set<ProductLog> productLog;

    @ManyToOne
    private ProductCategory category;

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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Set<ProductDetailImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(Set<ProductDetailImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }
}
