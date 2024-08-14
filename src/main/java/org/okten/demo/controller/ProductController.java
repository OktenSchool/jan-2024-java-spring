package org.okten.demo.controller;

import lombok.RequiredArgsConstructor;
import org.okten.demo.api.controller.ProductsApi;
import org.okten.demo.api.dto.ProductDto;
import org.okten.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
        return ResponseEntity.ok(productService.save(productDto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<ProductDto> getProduct(Long id) {
        return ResponseEntity.of(productService.findById(id));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @Override
    public ResponseEntity<ProductDto> modifyProduct(Long id, ProductDto productDto) {
        return ResponseEntity.of(productService.update(id, productDto));
    }

    @Override
    public ResponseEntity<ProductDto> modifyProductPartially(Long id, ProductDto productDto) {
        return ResponseEntity.of(productService.updatePartially(id, productDto));
    }
}
