package com.bnd.ecommerce.dto;

import com.bnd.ecommerce.entity.CreateUpdateTimeStamp;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

public class PhoneDto  {

  private long productId;

  @JsonBackReference
  @Valid private ProductDto productDto;

  private String screen;

  private String ram;
  private String camera;
  private String batteryCharge;
  private String operatingSystem;
  private String storageCapacity;
  private String frontFacingCamera;
  private String rearCamera;

  private String chip;
  private String sim;


  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public ProductDto getProductDto() {
    return productDto;
  }

  public void setProductDto(ProductDto productDto) {
    this.productDto = productDto;
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

  public String getSim() {
    return sim;
  }

  public void setSim(String sim) {
    this.sim = sim;
  }

  public String getPhotoImagePath() {
    if (productDto.getImage() == null || productDto.getId() == 0) return null;
    return "/phone-photos/" + productDto.getId() + "/" + productDto.getImage() ;
  }
}
