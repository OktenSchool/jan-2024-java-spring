package org.okten.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.okten.demo.entity.Review;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface ReviewMapper {

//    ReviewDto mapToDto(Review review);
//
//    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
//    Review mapToEntity(ReviewDto dto, Long productId);
}
