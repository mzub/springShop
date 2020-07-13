package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.User;
import ru.geekbrains.services.RoleService;
import ru.geekbrains.services.UserService;

@RequestMapping("/users")
@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String usersPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "user";
    }

    @GetMapping("edit")
    public String editUser(Model model, @RequestParam Long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        return "user";
    }

    @PostMapping
    public String save(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
