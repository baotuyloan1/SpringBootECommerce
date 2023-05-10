package com.bnd.ecommerce.controller;

import com.bnd.ecommerce.entity.*;
import com.bnd.ecommerce.service.*;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rawUI")
@Validated
public class RawController {
  private final ProductService productService;
  private final PhoneService phoneService;
  private final LaptopService laptopService;
  private final TabletService tabletService;
  private final BrandService brandService;

  private final CategoryService categoryService;

  public RawController(
      ProductService productService,
      PhoneService phoneService,
      LaptopService laptopService,
      TabletService tabletService,
      BrandService brandService,
      CategoryService categoryService) {
    this.productService = productService;
    this.phoneService = phoneService;
    this.laptopService = laptopService;
    this.tabletService = tabletService;
    this.brandService = brandService;
    this.categoryService = categoryService;
  }

  @GetMapping("/")
  public String showAll(Model model) {
    List<Product> products = productService.listProducts();
    List<Phone> phones = phoneService.listPhones();
    List<Tablet> tablets = tabletService.listTablets();
    List<Laptop> laptops = laptopService.listLaptops();
    List<Category> categories = categoryService.listCategories();
    model.addAttribute("listProducts", products);
    model.addAttribute("listTables", tablets);
    model.addAttribute("listLaptops", laptops);
    model.addAttribute("listPhones", phones);
    model.addAttribute("listCategories", categories);
    return viewPage(1, "id", "desc", 5, model);
  }

  @GetMapping("/newPhone")
  public String showCreatePhone(Model model) {
    Phone phone = new Phone();
    List<Brand> brandList = brandService.listBrands();
    model.addAttribute("phone", phone);
    model.addAttribute("brands", brandList);
    return "rawUI/new_phone";
  }

  @GetMapping("/newCategory")
  public String showCreateCategory(Model model) {
    Category category = new Category();
    List<Category> categories = categoryService.listCategories();
    model.addAttribute("category", category);
    model.addAttribute("categories", categories);
    return "rawUI/new_category";
  }

  @PostMapping("/saveCategory")
  public String saveCategory(@Valid @ModelAttribute("category") Category category) {
    categoryService.saveCategory(category);
    return "redirect:/rawUI/";
  }

  @DeleteMapping("/deleteCategory/{id}")
  public String deleteCategory() {
    return null;
  }

  @GetMapping("/page/{pageNum}")
  public String viewPage(
      @PathVariable("pageNum") @Positive(message = "Page number must be greater than 0")
          int pageNum,
      @RequestParam(value = "sortField", defaultValue = "id") String sortField,
      @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
      @RequestParam(value = "size", defaultValue = "5")
          @Max(value = 100, message = "Page size must be lest than 100")
          @Min(value = 5, message = "Page size must ben greater than 4")
          int size,
      Model model) {
    Page<Category> pageCategories = categoryService.listAll(pageNum, sortField, sortDir, size);
    Page<Product> pageProduct = productService.listAll(pageNum, sortField, sortDir, size);
    Page<Laptop> pageLaptop = laptopService.listAll(pageNum, sortField, sortDir, size);
    List<Category> listCategories = pageCategories.getContent();
    List<Product> listProducts = pageProduct.getContent();
    List<Laptop> listLaptops = pageLaptop.getContent();
    model.addAttribute("listCategories", listCategories);
    model.addAttribute("listProducts", listProducts);
    model.addAttribute("listLaptops", listLaptops);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    model.addAttribute("currentPageCategories", pageNum);
    model.addAttribute("totalPagesCategories", pageCategories.getTotalPages());
    return "rawUI/home";
  }

  @GetMapping("/login")
  public String viewLoginPage(){
    return "/rawUI/login";
  }


}
