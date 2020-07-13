package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.service.CategoryService;


@Controller
public class ShopController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/shop/{categoryId}")
    public String shopPage(Model model, @PathVariable Long categoryId) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categoryToDisplay", categoryService.findById(categoryId));
        return "shop";
    }

}
