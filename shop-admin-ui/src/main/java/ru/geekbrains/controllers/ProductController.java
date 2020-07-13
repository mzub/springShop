package ru.geekbrains.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.ProductService;

import java.io.IOException;

@RequestMapping("/products")
@Controller
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public CategoryService setCategoryService(CategoryService categoryService) {
        return this.categoryService = categoryService;
    }

    @GetMapping
    public String productsPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        model.addAttribute("product", new ProductRepr());
        model.addAttribute("categories", categoryService.findAll());
        return "product";
    }

    @GetMapping("edit")
    public String editUser (Model model, @RequestParam Long id) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "product";
    }

    @PostMapping
    public String save(ProductRepr productRepr) throws IOException {
        productService.save(productRepr);
        return "redirect:/products";
    }

    @DeleteMapping
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
