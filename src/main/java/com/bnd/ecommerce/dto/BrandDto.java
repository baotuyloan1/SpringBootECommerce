package com.bnd.ecommerce.dto;

import com.bnd.ecommerce.entity.CreateUpdateTimeStamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class BrandDto extends CreateUpdateTimeStamp {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
