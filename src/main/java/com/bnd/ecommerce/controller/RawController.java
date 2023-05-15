package com.bnd.ecommerce.controller;

import com.bnd.ecommerce.dto.EmployeeCreateDto;
import com.bnd.ecommerce.dto.EmployeeUpdateDto;
import com.bnd.ecommerce.entity.*;
import com.bnd.ecommerce.entity.employee.Employee;
import com.bnd.ecommerce.exception.NotFoundException;
import com.bnd.ecommerce.service.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rawUI")
public class RawController {
  private final ProductService productService;
  private final PhoneService phoneService;
  private final LaptopService laptopService;
  private final TabletService tabletService;
  private final BrandService brandService;
  private final CategoryService categoryService;
  private final EmployeeService employeeService;

  private final RoleService roleService;

  public RawController(
      ProductService productService,
      PhoneService phoneService,
      LaptopService laptopService,
      TabletService tabletService,
      BrandService brandService,
      CategoryService categoryService,
      EmployeeService employeeService,
      RoleService roleService) {
    this.productService = productService;
    this.phoneService = phoneService;
    this.laptopService = laptopService;
    this.tabletService = tabletService;
    this.brandService = brandService;
    this.categoryService = categoryService;
    this.employeeService = employeeService;
    this.roleService = roleService;
  }

  @GetMapping("/")
  public String showAll(Model model, Authentication authentication) {
    return viewPage(1, "id", "desc", 5, model, authentication);
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
      Model model,
      Authentication authentication) {
    if (pageNum < 0) pageNum = 1;
    Page<Category> pageCategories = categoryService.listAll(pageNum, sortField, sortDir, size);
    Page<Product> pageProduct = productService.listAll(pageNum, sortField, sortDir, size);
    Page<Laptop> pageLaptop = laptopService.listAll(pageNum, sortField, sortDir, size);
    List<Category> listCategories = pageCategories.getContent();
    List<Product> listProducts = pageProduct.getContent();
    List<Laptop> listLaptops = pageLaptop.getContent();
    List<Role> roles = roleService.listRoles();
    model.addAttribute("listRoles", roles);
    model.addAttribute("listCategories", listCategories);
    model.addAttribute("listProducts", listProducts);
    model.addAttribute("listLaptops", listLaptops);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    model.addAttribute("currentPageCategories", pageNum);
    model.addAttribute("totalPagesCategories", pageCategories.getTotalPages());
    Page<Employee> employeePage = employeeService.listAll(size, pageNum, sortField, sortDir);
    model.addAttribute("listEmployees", employeePage.getContent());
    //    for ( Employee employee: employeePage.getContent() ) {
    //        for (EmployeeRole employeeRole :employee.getEmployeeRoles() ) {
    //
    //        }
    //    }
    ArrayList<String> currentRoles = new ArrayList<>();
    if (authentication != null) {
      for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
        currentRoles.add(grantedAuthority.getAuthority());
      }
    }

    model.addAttribute("currentRoles", currentRoles);

    return "rawUI/home";
  }

  @GetMapping("/login")
  public String viewLoginPage() {
    return "/rawUI/login";
  }

  @GetMapping("/admin/newEmployee")
  public String showCreateEmployeePage(Model model) {
    model.addAttribute("employee", new EmployeeCreateDto());

    model.addAttribute("allRoles", roleService.listRoles());
    return "rawUI/employee/new_employee";
  }

  @Validated
  @PostMapping("/admin/newEmployee")
  public String createEmployee(
      @Valid @ModelAttribute("employee") EmployeeCreateDto employeeCreateDto,
      BindingResult bindingResult,
      Authentication authentication,
      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("allRoles", roleService.listRoles());
      return "rawUI/employee/new_employee";
    }
    Employee employee = employeeService.createNewEmployee(employeeCreateDto, authentication);
    if (employee != null) return "redirect:/rawUI/";
    else return "rawUI/employee/new_employee";
  }

  @GetMapping("/admin/newRole")
  public String showNewRolePage(Model model) {
    model.addAttribute("role", new Role());
    return "/rawUI/role/new_role";
  }

  @PostMapping("/admin/saveRole")
  public String saveRole(@Valid @ModelAttribute("role") Role role) {
    Role savedRole = roleService.save(role);
    return savedRole != null ? "redirect:/rawUI/" : "/rawUI/role/new_role";
  }

  @GetMapping("/admin/editRole/{id}")
  public String showEditRolePage(Model model, @PathVariable("id") int idRole) {
    Role role = roleService.findById(idRole);
    model.addAttribute("role", role);
    return "rawUI/role/edit_role";
  }

  @PostMapping("/admin/editRole/{id}")
  public String updateRole(@Valid @ModelAttribute("role") Role role, @PathVariable("id") int id) {
    Role updatedRole = roleService.findById(id);
    if (updatedRole != null) {
      role.setId(id);
      roleService.save(role);
      return "redirect:/rawUI/";
    } else {
      throw new NotFoundException("The role does not exist in the system");
    }
  }

  @GetMapping("/admin/deleteRole/{id}")
  public String deleteRole(@PathVariable("id") int id) {
    boolean isDeleted = roleService.deleteById(id);
    if (isDeleted) {
      return "redirect:/rawUI/";
    } else {
      throw new RuntimeException("Unsuccessful !");
    }
  }

  @GetMapping("/admin/editEmployee/{id}")
  public String editEmployee(
      @Positive(message = "Id employee must higher than 0") @PathVariable("id") long id,
      Model model) {

    EmployeeUpdateDto employeeUpdateDto = employeeService.getEmployeePostDtoById(id);
    model.addAttribute("employeeUpdateDto", employeeUpdateDto);

    model.addAttribute("ownRoles", employeeUpdateDto.getRoles());
    List<Role> allRoles = roleService.listRoles();
    model.addAttribute("allRoles", allRoles);
    return "rawUI/employee/edit_employee";
  }

  @GetMapping("/errorPage/403")
  public String errorPage403() {
    return "rawUI/403";
  }

  @PostMapping("/admin/editEmployee/{id}")
  public String updateEmployeeNoPassword(
      @PathVariable("id") Long id,
      @ModelAttribute EmployeeUpdateDto employeeUpdateDto,
      Authentication authentication) {
    Employee employee = employeeService.updateNoPassword(id, employeeUpdateDto, authentication);
    if (employee != null) {
      return "redirect:/rawUI/";
    }
    throw new RuntimeException("Failed update");
  }

  @GetMapping("/admin/deleteEmployee/{id}")
  public String deleteEmployee(@PathVariable("id") long id, Authentication authentication) {
    boolean status = employeeService.deleteById(id, authentication);
    if (status) {
      return "redirect:/rawUI/";
    } else {
      throw new RuntimeException("Delete Unsuccessfull");
    }
  }
}
