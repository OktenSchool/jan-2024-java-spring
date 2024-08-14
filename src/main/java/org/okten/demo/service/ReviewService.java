package org.okten.demo.service;

import lombok.RequiredArgsConstructor;
import org.okten.demo.entity.Review;
import org.okten.demo.mapper.ReviewMapper;
import org.okten.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

//    public List<ReviewDto> getReviews(Long productId) {
//        return reviewRepository
//                .findAllByProductId(productId)
//                .stream()
//                .map(reviewMapper::mapToDto)
//                .toList();
//    }
//
//    public ReviewDto createReview(Long productId, ReviewDto reviewDto) {
//        Review review = reviewMapper.mapToEntity(reviewDto, productId);
//        Review createdReview = reviewRepository.insert(review);
//        return reviewMapper.mapToDto(createdReview);
//    }
}
