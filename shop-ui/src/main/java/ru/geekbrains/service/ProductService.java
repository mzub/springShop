package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Picture;
import ru.geekbrains.model.PictureData;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.util.NotFoundException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream().map(product -> new ProductRepr(product)).collect(Collectors.toList());
    }

    @Transactional
    public void save(ProductRepr productRepr) throws IOException {
        Product product = (productRepr.getId() != null) ? productRepository.findById(productRepr.getId())
                .orElseThrow(NotFoundException::new) : new Product();
        product.setTitle(productRepr.getName());
//        Category category = (productRepr.getCategoryRepr().getId() != null) ? categoryRepository.findById(productRepr.getCategoryRepr().getId())
//                .orElseThrow(NotFoundException::new) : new Category();
        product.setCategory(productRepr.getCategory());
        product.setCost(productRepr.getPrice());
        if (productRepr.getNewPictures() != null) {
            for (MultipartFile newPicture : productRepr.getNewPictures()) {
                if (product.getPictures() == null) {
                    product.setPictures(new ArrayList<>());
                }
                if(!productRepr.getNewPictures()[0].getOriginalFilename().equals("")) {
                    product.getPictures().add(new Picture(newPicture.getOriginalFilename(),
                            newPicture.getContentType(), new PictureData(newPicture.getBytes()), product));
                }
            }
        }
        productRepository.save(product);
    }

    @Transactional
    public ProductRepr findById(Long id) {
        return productRepository.findById(id).map(ProductRepr::new).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow(NotFoundException::new));
    }
}
