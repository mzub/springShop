package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.Role;
import ru.geekbrains.repo.RoleRepository;
import ru.geekbrains.util.NotFoundException;

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

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void delete(Long id) {
        roleRepository.delete(findById(id));
    }
}
