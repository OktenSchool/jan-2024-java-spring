package org.okten.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.okten.demo.api.event.producer.IProductEventsProducer;
import org.okten.demo.api.rest.dto.ProductDto;
import org.okten.demo.entity.Product;
import org.okten.demo.entity.ProductAvailability;
import org.okten.demo.mapper.ProductMapper;
import org.okten.demo.repository.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mock
    private MailService mailService;

    @Mock
    private IProductEventsProducer productEventsProducer;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Test
    void save() {
        productService.save(new ProductDto()
                .name("test")
                .price(2.99)
                .availability(ProductDto.AvailabilityEnum.AVAILABLE));

        verify(productRepository).save(productCaptor.capture());
        verify(mailService).sendMail(any());

        Product savedProduct = productCaptor.getValue();

        assertEquals("test", savedProduct.getName());
    }

    @Test
    void findById() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("test");
        existingProduct.setPrice(3.99);
        existingProduct.setAvailability(ProductAvailability.AVAILABLE);
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingProduct));

        Optional<ProductDto> result = productService.findById(1L);

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(foundProduct -> {
                    assertEquals(existingProduct.getId(), foundProduct.getId());
                    assertEquals(existingProduct.getName(), foundProduct.getName());
                    assertEquals(existingProduct.getPrice(), foundProduct.getPrice());
                    assertEquals(existingProduct.getAvailability().toString(), foundProduct.getAvailability().toString());
                });
    }
}