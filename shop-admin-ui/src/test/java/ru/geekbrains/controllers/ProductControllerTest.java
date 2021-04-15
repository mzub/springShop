package ru.geekbrains.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.util.NotFoundException;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @WithMockUser(value = "admin", password = "100", roles = "ADMIN")
    @Test
    public void testSaveNewProduct() throws Exception {
        mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "new product")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));
        Product product = new Product();
        product.setTitle("new product");
        Optional<Product> actualProduct = productRepository.findOne(Example.of(product));

        assertTrue(actualProduct.isPresent());
        assertEquals("new product", actualProduct.get().getTitle());


    }

    @WithMockUser(value = "admin", password = "100", roles = "ADMIN")
    @Test
    public void testDeleteProduct() throws Exception {

        Product product = new Product();
        product.setTitle("new product");
        long id = productRepository.save(product).getId();

        assertTrue(productRepository.existsById(id));

        mvc.perform(delete("/products")
                .param("id", String.valueOf(id))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));

        assertFalse(productRepository.existsById(id));
    }

    @WithMockUser(value = "admin", password = "100", roles = "ADMIN")
    @Test
    public void testEditProduct() throws Exception {

        Product product = new Product();
        product.setTitle("new product");
        product.setCost(new BigDecimal(444));
        long id = productRepository.save(product).getId();
        mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id",String.valueOf(id))
                .param("cost", String.valueOf(new BigDecimal(555)))
                .param("title","second title")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));
        Optional<Product> actualProduct = productRepository.findById(id);
        assertEquals(new BigDecimal(555), actualProduct.get().getCost());
    }
}
