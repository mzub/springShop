package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.Role;
import ru.geekbrains.repo.RoleRepository;

import java.util.List;

@Service
public class RoleService {
   private RoleRepository roleRepository;

   @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
       return roleRepository.findAll();
    }
}
