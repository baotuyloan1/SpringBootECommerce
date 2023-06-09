package com.bnd.ecommerce.config;

import com.bnd.ecommerce.service.ProductService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    exposeDirectory("phone-photos", registry);
    exposeDirectory("user-photos", registry);
    exposeDirectory("tablet-photos", registry);
  }

  private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
    Path uploadDir = Paths.get(dirName);
    String uploadPath = uploadDir.toFile().getAbsolutePath();
    if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
    registry
        .addResourceHandler("/" + dirName + "/**")
        .addResourceLocations("file:/" + uploadPath + "/");
  }
}
