package com.bnd.ecommerce.dto;

import com.bnd.ecommerce.entity.CreateUpdateTimeStamp;
import com.bnd.ecommerce.entity.Product;

import javax.validation.constraints.NotBlank;

public class PhoneDto extends CreateUpdateTimeStamp {

  private int id;

  @NotBlank
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
  private String sim;
}
