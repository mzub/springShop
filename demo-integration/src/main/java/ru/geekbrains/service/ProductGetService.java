package ru.geekbrains.service;

import ru.geekbrains.model.Product;

import java.util.List;

public interface ProductGetService {
    public List<Product> findProducts();
}
