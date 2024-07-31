package org.okten.demo.controller;

import lombok.RequiredArgsConstructor;
import org.okten.demo.entity.Product;
import org.okten.demo.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        return ResponseEntity.of(productRepository.findById(productId));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
    ) {

        if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(productRepository.findAllByPriceBetween(minPrice, maxPrice));
        } else if (minPrice != null) {
            return ResponseEntity.ok(productRepository.findAllByPriceGreaterThan(minPrice));
        } else if (maxPrice != null) {
            return ResponseEntity.ok(productRepository.findAllByPriceLessThan(maxPrice));
        } else {
            return ResponseEntity.ok(productRepository.findAll());
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product productUpdateWith) {
        return ResponseEntity.of(
                productRepository
                        .findById(productId)
                        .map(existingProduct -> {
                            existingProduct.setName(productUpdateWith.getName());
                            existingProduct.setPrice(productUpdateWith.getPrice());
                            return productRepository.save(existingProduct);
                        }));
    }
}
