package com.bnd.ecommerce.controller.raw;

import com.bnd.ecommerce.dto.BrandDto;
import com.bnd.ecommerce.dto.CategoryDto;
import com.bnd.ecommerce.dto.PhoneDto;
import com.bnd.ecommerce.dto.ProductDto;
import com.bnd.ecommerce.entity.Phone;
import com.bnd.ecommerce.entity.Product;
import com.bnd.ecommerce.exception.CreateFailException;
import com.bnd.ecommerce.exception.DeleteFailException;
import com.bnd.ecommerce.service.BrandService;
import com.bnd.ecommerce.service.CategoryService;
import com.bnd.ecommerce.service.PhoneService;
import com.bnd.ecommerce.service.ProductService;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rawUI/products")
public class ProductController {

  private final ProductService productService;
  private final PhoneService phoneService;
  private final BrandService brandService;
  private final CategoryService categoryService;

  private static final String REDIRECT_PRODUCTS = "redirect:/rawUI/products/1";

  public ProductController(
      ProductService productService,
      PhoneService phoneService,
      BrandService brandService,
      CategoryService categoryService) {
    this.productService = productService;
    this.phoneService = phoneService;
    this.brandService = brandService;
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
    List<BrandDto> brandDtoList = brandService.brandDtoList();
    Set<CategoryDto> categoryDtoSet = categoryService.categoryDtoSet();
    model.addAttribute("brandDtoList", brandDtoList);
    model.addAttribute("categoryDtoSet", categoryDtoSet);
    productDto.setPhoneDto(phoneDto);
    model.addAttribute("phoneDto", phoneDto);
    return "rawUI/product/new_phone";
  }

  @PostMapping("/createPhone")
  public String savePhone(
      @Valid @ModelAttribute("phoneDto") PhoneDto phoneDto,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      List<BrandDto> brandDtoList = brandService.brandDtoList();
      Set<CategoryDto> categoryDtoSet = categoryService.categoryDtoSet();
      model.addAttribute("brandDtoList", brandDtoList);
      model.addAttribute("categoryDtoSet", categoryDtoSet);
      return "rawUI/product/new_phone";
    }
    Phone savedPhone = phoneService.save(phoneDto);
    if (savedPhone != null) return REDIRECT_PRODUCTS;
    else throw new CreateFailException("Create phone fail");
  }

  @GetMapping("/editProduct/{id}")
  public String showEditProduct(@PathVariable("id") long id, Model model) {
    Object object = productService.findById(id);
    List<BrandDto> brandDtoList = brandService.brandDtoList();
    Set<CategoryDto> categoryDtoSet = categoryService.categoryDtoSet();
    model.addAttribute("brandDtoList", brandDtoList);
    model.addAttribute("categoryDtoSet", categoryDtoSet);
    if (object instanceof PhoneDto phoneDto) {
      model.addAttribute("phoneDto", phoneDto);
      return "rawUI/product/edit_phone";
    } else {
      return null;
    }
  }

  @PostMapping("/updatePhone/{id}")
  public String updateProduct(
      @ModelAttribute("phoneDto") PhoneDto phoneDto, @PathVariable("id") long id, Model model) {
    phoneDto.getProductDto().setId(id);
    Phone savedPhone = phoneService.save(phoneDto);
    if (savedPhone != null) {
      return REDIRECT_PRODUCTS;
    } else {
      List<BrandDto> brandDtoList = brandService.brandDtoList();
      Set<CategoryDto> categoryDtoSet = categoryService.categoryDtoSet();
      model.addAttribute("brandDtoList", brandDtoList);
      model.addAttribute("categoryDtoSet", categoryDtoSet);
      return "rawUI/product/edit_phone";
    }
  }

  @GetMapping("/deleteProduct/{id}")
  public String deleteProduct(@PathVariable("id") long id) {
    boolean isDeleted = productService.deleteProductById(id);
    if (isDeleted) return REDIRECT_PRODUCTS;
    else throw new DeleteFailException("Delete Product fail");
  }
}
