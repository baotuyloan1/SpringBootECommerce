package com.bnd.ecommerce.controller;

import com.bnd.ecommerce.entity.*;
import com.bnd.ecommerce.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/rawUI")
public class RawController {
    private final ProductService productService;
    private final PhoneService phoneService;
    private final LaptopService laptopService;
    private final TabletService tabletService;
    private final BrandService brandService;

    private final CategoryService categoryService;

    public RawController(ProductService productService, PhoneService phoneService, LaptopService laptopService, TabletService tabletService, BrandService brandService, CategoryService categoryService) {
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
        return "rawUI/home";
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
        return "rawUI/home";
    }

    @DeleteMapping("/deleteCategory/{id}")
    public String deleteCategory() {
        return null;
    }


}


