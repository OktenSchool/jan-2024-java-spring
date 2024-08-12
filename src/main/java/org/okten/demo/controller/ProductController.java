package org.okten.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.demo.dto.ReviewDto;
import org.okten.demo.dto.UpsertProductDto;
import org.okten.demo.dto.ProductDto;
import org.okten.demo.service.ProductService;
import org.okten.demo.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    private final ProductService productService;

    private final ReviewService reviewService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.of(productService.findById(productId));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
    ) {

        if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceBetween(minPrice, maxPrice));
        } else if (minPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceGreaterThan(minPrice));
        } else if (maxPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceLessThan(maxPrice));
        } else {
            return ResponseEntity.ok(productService.findAllProducts());
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody UpsertProductDto product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @Valid @RequestBody UpsertProductDto productUpdateWith) {
        return ResponseEntity.of(productService.update(productId, productUpdateWith));
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviews(productId));
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable Long productId, @Valid @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.createReview(productId, reviewDto));
    }
}
