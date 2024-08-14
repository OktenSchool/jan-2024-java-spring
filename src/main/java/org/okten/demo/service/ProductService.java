package org.okten.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.demo.api.dto.ProductDto;
import org.okten.demo.dto.SendMailDto;
import org.okten.demo.entity.Product;
import org.okten.demo.mapper.ProductMapper;
import org.okten.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final MailService mailService;

    public Optional<ProductDto> findById(Long id) {
        return productRepository
                .findById(id)
                .map(productMapper::mapToDto);
    }

    public List<ProductDto> findAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public List<ProductDto> findAllByPriceBetween(Double min, Double max) {
        return productRepository
                .findAllByPriceBetween(min, max)
                .stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public List<ProductDto> findAllByPriceGreaterThan(Double value) {
        return productRepository
                .findAllByPriceGreaterThan(value)
                .stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public List<ProductDto> findAllByPriceLessThan(Double value) {
        return productRepository
                .findAllByPriceLessThan(value)
                .stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    @Transactional
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        SendMailDto mailDto = SendMailDto.builder()
                .subject("New product created")
                .text("Product '%s' was created with price %s".formatted(product.getName(), product.getPrice()))
                .recipient(product.getOwner())
                .build();
        mailService.sendMail(mailDto);
        return productMapper.mapToDto(savedProduct);
    }

    @Transactional
    public Optional<ProductDto> update(Long productId, ProductDto productUpdateWith) {
        return productRepository
                .findById(productId)
                .map(product -> productMapper.update(product, productUpdateWith))
                .map(productMapper::mapToDto);
    }

    @Transactional
    public Optional<ProductDto> updatePartially(Long productId, ProductDto productUpdateWith) {
        return productRepository
                .findById(productId)
                .map(product -> productMapper.updatePartially(product, productUpdateWith))
                .map(productMapper::mapToDto);
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }
}
