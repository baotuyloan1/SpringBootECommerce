package com.bnd.ecommerce.controller.raw;

import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.service.PhoneService;
import com.bnd.ecommerce.service.ProductService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rawUI/products")
public class ProductController {

  private final ProductService productService;
 private final PhoneService phoneService;

  private final static String REDIRECT_PRODUCTS = "/rawUI/products/1";

  public ProductController(ProductService productService, PhoneService phoneService) {
    this.productService = productService;
    this.phoneService = phoneService;
  }

  @GetMapping("/{numPage}")
  public String showProducts(
      Model model,
      @RequestParam(value = "numbersItem", defaultValue = "10") int numbersItem,
      @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
      @RequestParam(value = "sortField", defaultValue = "id") String sortField,
      @PathVariable(value = "numPage") int numPage) {
    List<Product> products =
        productService.listAll(numPage, sortField, sortDir, numbersItem).getContent();
    model.addAttribute("products", products);
    return "rawUI/product/products";
  }

  @GetMapping("/newPhone")
  public String showNewPhonePage(Model model) {
    Phone phone = new Phone();
    model.addAttribute("phone", phone);
    return "rawUI/product/new_phone";
  }

  @PostMapping("/savePhone")
  public String savePhone(@ModelAttribute PhoneDto phone){
    Phone savedPhone = phoneService.save()
    return REDIRECT_PRODUCTS;
  }
}
