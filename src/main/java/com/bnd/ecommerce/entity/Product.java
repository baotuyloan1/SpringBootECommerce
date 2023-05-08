package com.bnd.ecommerce.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Product extends CreateUpdateTimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String price;



    @OneToMany(mappedBy = "product")
    private Set<ProductLog> productLog;

    @ManyToOne
    private ProductCategory category;

    @OneToMany(mappedBy = "product")
    private Set<ProductDetailImage>  productDetailImages;


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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
