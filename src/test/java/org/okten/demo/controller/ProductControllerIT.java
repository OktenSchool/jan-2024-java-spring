package org.okten.demo.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.okten.demo.config.TestcontainersConfig;
import org.okten.demo.entity.Product;
import org.okten.demo.entity.ProductAvailability;
import org.okten.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestcontainersConfig.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void cleanUpDatabase() {
        productRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void getProducts() {
        Product createdProduct = new Product();
        createdProduct.setName("test-product");
        createdProduct.setPrice(1.99);
        createdProduct.setAvailability(ProductAvailability.AVAILABLE);
        createdProduct.setOwner("test-owner");
        productRepository.save(createdProduct);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[*].name", contains(equalTo(createdProduct.getName()))));
    }

    @SneakyThrows
    @Test
    void getProduct() {
        Product createdProduct = new Product();
        createdProduct.setName("test-product");
        createdProduct.setPrice(1.99);
        createdProduct.setAvailability(ProductAvailability.AVAILABLE);
        createdProduct.setOwner("test-owner");
        createdProduct = productRepository.save(createdProduct);

        mockMvc.perform(get("/products/{id}", createdProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(createdProduct.getName())));
    }
}
