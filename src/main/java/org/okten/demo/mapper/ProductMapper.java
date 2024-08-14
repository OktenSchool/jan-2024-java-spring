package org.okten.demo.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.okten.demo.api.dto.ProductDto;
import org.okten.demo.entity.Product;

@Mapper
public interface ProductMapper {

    ProductDto mapToDto(Product product);

    Product mapToProduct(ProductDto dto);

    Product update(@MappingTarget Product product, ProductDto productDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updatePartially(@MappingTarget Product product, ProductDto productDto);
}
