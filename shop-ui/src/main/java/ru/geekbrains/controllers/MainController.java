package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.service.CategoryService;


@Controller
public class MainController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
