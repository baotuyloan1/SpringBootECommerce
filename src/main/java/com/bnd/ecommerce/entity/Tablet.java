package com.bnd.ecommerce.entity;

import com.bnd.ecommerce.entity.Product;

import javax.persistence.*;

@Entity
public class Tablet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Product product;
    private String screen;
    private String ram;
    private String camera;
    private String batteryCharge;
    private String operatingSystem;

    private String storageCapacity;

    private String frontFacingCamera;

    private String rearCamera;


    private String chip;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(String batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getFrontFacingCamera() {
        return frontFacingCamera;
    }

    public void setFrontFacingCamera(String frontFacingCamera) {
        this.frontFacingCamera = frontFacingCamera;
    }

    public String getRearCamera() {
        return rearCamera;
    }

    public void setRearCamera(String rearCamera) {
        this.rearCamera = rearCamera;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }
}
