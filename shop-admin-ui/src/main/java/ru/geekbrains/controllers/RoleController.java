package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.Role;
import ru.geekbrains.services.RoleService;

@RequestMapping("/roles")
@Controller
public class RoleController {

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String rolesPage(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "roles";
    }

    @GetMapping("new")
    public String createRole(Model model) {
        model.addAttribute("role", new Role());
        return "role";
    }

    @GetMapping("edit")
    public String editRole(Model model, @RequestParam Long id) {
        model.addAttribute(roleService.findById(id));
        return "role";
    }

    @PostMapping
    public String save(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }

    @DeleteMapping
    public String deleteRole(@RequestParam Long id) {
        roleService.delete(id);
        return "redirect:/roles";
    }
}
