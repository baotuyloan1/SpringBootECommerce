package com.bnd.ecommerce.controller.raw;

import com.bnd.ecommerce.dto.StockDto;
import com.bnd.ecommerce.entity.stock.Stock;
import com.bnd.ecommerce.service.StockService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/stocks/")
public class StockController {

  private final StockService stockService;

  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @GetMapping("/{numPage}")
  public String showStocks(
      Model model,
      @RequestParam(value = "itemsNumber", defaultValue = "10") int numbersItem,
      @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
      @RequestParam(value = "sortField", defaultValue = "id") String sortField,
      @PathVariable(value = "numPage") int numPage,
      @RequestParam(value = "keyword", defaultValue = "") String keyword) {
    Page<Stock> stockPage =
        stockService.stockPage(numPage, sortField, sortDir, numbersItem, keyword);

    model.addAttribute("currentPage", numPage);
    model.addAttribute("stockPage", stockPage);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    model.addAttribute("numberElement", numbersItem);
    model.addAttribute("stockList", stockPage.getContent());
    model.addAttribute("keyword", keyword);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("currentPage", numPage);
    model.addAttribute("totalPages", stockPage.getTotalPages());
    return "rawUI/stock/stocks";
  }

  @GetMapping("/newStock")
  public String showNewStock(Model model) {
    model.addAttribute("stockDto", new StockDto());
    return "rawUI/stock/new_stock";
  }
}
