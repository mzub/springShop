package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.util.NotFoundException;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;


    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream().map(product -> new ProductRepr(product)).collect(Collectors.toList());
    }


    @Transactional
    public ProductRepr findById(Long id) {
        return productRepository.findById(id).map(ProductRepr::new).orElseThrow(NotFoundException::new);
    }

}
