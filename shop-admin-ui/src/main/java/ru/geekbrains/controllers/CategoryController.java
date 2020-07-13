package ru.geekbrains.controllers;

import liquibase.pro.packaged.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.Category;
import ru.geekbrains.services.CategoryService;

@RequestMapping("/categories")
@Controller
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String categoriesPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping("new")
    public String createCategory(Model model) {
        model.addAttribute("category", new Category());
        return "category";
    }

    @GetMapping("edit")
    public String editCategory(Model model, @RequestParam Long id) {
        model.addAttribute("category", categoryService.findById(id));
        return "category";
    }

    @PostMapping
    public String save(Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @DeleteMapping
    public String deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
