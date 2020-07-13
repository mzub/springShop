package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.Category;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.repr.CategoryRepr;
import ru.geekbrains.util.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
       this.categoryRepository = categoryRepository;
    }

    public List<CategoryRepr> findAll() {
        return categoryRepository.findAll().stream().map(CategoryRepr::new).collect(Collectors.toList());
    }

    public CategoryRepr findById(Long id) {
        return categoryRepository.findById(id).map(CategoryRepr::new).orElseThrow(NotFoundException::new);
    }
}
