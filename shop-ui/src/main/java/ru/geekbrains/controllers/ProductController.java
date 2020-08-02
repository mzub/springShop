package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.StockService;


@Controller
public class ProductController {

    private ProductService productService;

    private StockService stockService;

    @Autowired
    public ProductController(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;
    }


    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable Long id) {
        ProductRepr productRepr = productService.findById(id);
        productRepr.setCountInStock(stockService.getStockInfo(productRepr.getId()).getCount());
        model.addAttribute("product", productRepr);
        return "product-details";
    }
}
