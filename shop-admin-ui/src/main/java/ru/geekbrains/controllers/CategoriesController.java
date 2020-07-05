package ru.geekbrains.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoriesController {
    @GetMapping("/categories")
    public String categoriesPage(Model model) {
        return "categories";
    }
}
