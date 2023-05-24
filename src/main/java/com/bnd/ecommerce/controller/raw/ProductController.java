package com.bnd.ecommerce.controller.raw;

import com.bnd.ecommerce.dto.*;
import com.bnd.ecommerce.entity.Category;
import com.bnd.ecommerce.entity.Laptop;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.exception.CreateFailException;
import com.bnd.ecommerce.exception.DeleteFailException;
import com.bnd.ecommerce.service.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/rawUI/products")
public class ProductController {

  private final ProductService productService;
  private final PhoneService phoneService;
  private final BrandService brandService;

  private final LaptopService laptopService;
  private final CategoryService categoryService;

  private static final String REDIRECT_PRODUCTS = "redirect:/rawUI/products/1";
  private static final String VIEW_NEW_PHONE = "rawUI/product/new_phone";
  private static final String VIEW_NEW_LAPTOP = "rawUI/product/new_laptop";

  public ProductController(
      ProductService productService,
      PhoneService phoneService,
      BrandService brandService,
      LaptopService laptopService,
      CategoryService categoryService) {
    this.productService = productService;
    this.phoneService = phoneService;
    this.brandService = brandService;
    this.laptopService = laptopService;
    this.categoryService = categoryService;
  }

  @GetMapping("/{numPage}")
  public String showProducts(
      Model model,
      @RequestParam(value = "itemsNumber", defaultValue = "10") int numbersItem,
      @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
      @RequestParam(value = "sortField", defaultValue = "id") String sortField,
      @PathVariable(value = "numPage") int numPage,
      @RequestParam(value = "keyword", defaultValue = "") String keyword) {
    Page<Product> productPage =
        productService.listAll(numPage, sortField, sortDir, numbersItem, keyword);
    List<Product> products = productPage.getContent();
    model.addAttribute("productList", products);
    model.addAttribute("keyword", keyword);
    model.addAttribute("currentPage", numPage);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    model.addAttribute("numberElement", numbersItem);
    model.addAttribute("totalPages", productPage.getTotalPages());
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("rootCategoryList", categoryService.getRootCategoryList());

    return "rawUI/product/products";
  }

  @GetMapping("/newPhone")
  public String showNewPhonePage(Model model) {
    BrandDto brandDto = new BrandDto();
    ProductDto productDto = new ProductDto();
    PhoneDto phoneDto = new PhoneDto();
    productDto.setBrandDto(brandDto);
    phoneDto.setProductDto(productDto);
    productDto.setPhoneDto(phoneDto);
    loadData(model);
    model.addAttribute("phoneDto", phoneDto);
    return VIEW_NEW_PHONE;
  }

  private void loadData(Model model) {
    List<BrandDto> brandDtoList = brandService.brandDtoList();
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    List<Category> rootCategoryList = categoryService.getRootCategoryList();
    for (Category item : rootCategoryList) {
      categoryService.getLevelCategory(item, 0, categoryDtoList);
    }
    model.addAttribute("brandDtoList", brandDtoList);
    model.addAttribute("categoryDtoList", categoryDtoList);
  }

  @PostMapping("/createPhone")
  public String savePhone(
      @Valid @ModelAttribute("phoneDto") PhoneDto phoneDto,
      BindingResult bindingResult,
      @RequestParam("imageProduct") MultipartFile mainImage,
      @RequestParam("imagesDetail") MultipartFile[] imagesDetail,
      Model model) {
    if (bindingResult.hasErrors() || mainImage.isEmpty()) {
      loadData(model);
      if (mainImage.isEmpty()) {
        model.addAttribute("imageEmptyError", "Image can't empty");
        model.addAttribute("imageIsEmpty", true);
      }
      return VIEW_NEW_PHONE;
    }
    Phone savedPhone;
    if (!mainImage.isEmpty()) {
      savedPhone = phoneService.create(phoneDto, mainImage, imagesDetail);
      if (savedPhone != null) return REDIRECT_PRODUCTS;
      else throw new CreateFailException("Create phone fail");
    }
    return VIEW_NEW_PHONE;
  }

  @GetMapping("/editProduct/{id}")
  public String showEditProduct(@PathVariable("id") long id, Model model) {
    Object object = productService.findById(id);
    loadData(model);
    if (object instanceof PhoneDto phoneDto) {
      model.addAttribute("phoneDto", phoneDto);
      return "rawUI/product/edit_phone";
    } else if (object instanceof LaptopDto laptopDto) {
      model.addAttribute("laptopDto", laptopDto);
      return "rawUI/product/edit_laptop";
    } else {
      return null;
    }
  }

  @PostMapping("/updatePhone")
  public String updatePhone(
      @ModelAttribute("phoneDto") PhoneDto phoneDto,
      Model model,
      @RequestParam("imageProduct") MultipartFile multipartFile) {
    Phone savedPhone = phoneService.update(phoneDto, multipartFile);
    if (savedPhone != null) {
      return REDIRECT_PRODUCTS;
    } else {
      loadData(model);
      return "rawUI/product/edit_phone";
    }
  }

  @GetMapping("/deleteProduct/{id}")
  public String deleteProduct(@PathVariable("id") long id) {
    boolean isDeleted = productService.deleteProductById(id);
    if (isDeleted) return REDIRECT_PRODUCTS;
    else throw new DeleteFailException("Delete Product fail");
  }

  @GetMapping("/newLaptop")
  public String showNewLaptop(Model model) {
    BrandDto brandDto = new BrandDto();
    ProductDto productDto = new ProductDto();
    LaptopDto laptopDto = new LaptopDto();
    productDto.setBrandDto(brandDto);
    laptopDto.setProductDto(productDto);
    loadData(model);
    model.addAttribute("laptopDto", laptopDto);
    return VIEW_NEW_LAPTOP;
  }

  @PostMapping("/createLaptop")
  public String saveLaptop(
      @Valid @ModelAttribute("laptopDto") LaptopDto laptopDto,
      BindingResult bindingResult,
      @RequestParam("imageProduct") MultipartFile mainImage,
      @RequestParam("imagesDetail") MultipartFile[] imagesDetail,
      Model model) {
    if (bindingResult.hasErrors() || mainImage.isEmpty()) {
      loadData(model);
      if (mainImage.isEmpty()) {
        model.addAttribute("imageEmptyError", "Image can't empty");
        model.addAttribute("imageIsEmpty", true);
      }
      return VIEW_NEW_LAPTOP;
    }
    Laptop savedLaptop;
    if (!mainImage.isEmpty()) {
      savedLaptop = laptopService.create(laptopDto, mainImage, imagesDetail);
      if (savedLaptop != null) return REDIRECT_PRODUCTS;
      else throw new CreateFailException("Create laptop fail");
    }
    return REDIRECT_PRODUCTS;
  }
}
