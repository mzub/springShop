package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repr.ProductRepr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productService = new ProductService();
        productRepository = mock(ProductRepository.class);
        productService.setProductRepository(productRepository);
    }

    @Test
    public void testFindById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Some Category");
        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(2134));
        expectedProduct.setCategory(category);
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setTitle("Some Product Title");

        when(productRepository.findById(eq(1L)))
                .thenReturn(Optional.of(expectedProduct));

        ProductRepr productFromService = productService.findById(1L);
        assertNotNull(productFromService);
        assertEquals(expectedProduct.getId(), productFromService.getId());
        assertEquals(expectedProduct.getCost(), productFromService.getPrice());
        assertEquals(expectedProduct.getCategory().getName(), productFromService.getCategoryName());
        assertEquals(expectedProduct.getPictures(), productFromService.getPictures());
        assertEquals(expectedProduct.getTitle(), productFromService.getName());
    }
}
