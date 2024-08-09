package org.okten.demo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.demo.dto.ReviewDto;
import org.okten.demo.dto.SendMailDto;
import org.okten.demo.service.MailService;
import org.okten.demo.service.ProductService;
import org.okten.demo.service.ReviewService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

//@Component
@RequiredArgsConstructor
@Slf4j
public class SendProductStats {

    private final ProductService productService;

    private final ReviewService reviewService;

    private final MailService mailService;

    @Scheduled(fixedDelay = 20L, timeUnit = TimeUnit.SECONDS)
    public void run() {
        log.info("Sending product stats...");

        productService
                .findAllProducts()
                .stream()
                .collect(toMap(
                        identity(),
                        productDto ->
                                reviewService.getReviews(productDto.getId())
                                        .stream()
                                        .mapToInt(ReviewDto::getRating)
                                        .average()
                                        .orElse(-1)))
                .forEach((productDto, averageRating) -> {
                    if (averageRating != -1) {
                        mailService.sendMail(SendMailDto.builder()
                                .recipient(productDto.getOwner())
                                .subject("Product '%s' average rating update".formatted(productDto.getName()))
                                .text("Current rating is: %s".formatted(averageRating))
                                .build());
                    }
                });
    }

    // fixedDelay = 1 hour
    // 1 run - 13:00 - 13:05
    // 2 run - 14:05 - 14:10
    // 3 run - 15:10 - 15:15

    // fixedRate = 1 hour
    // 1 run - 13:00 - 13:05
    // 2 run - 14:00 - 14:05
    // 3 run - 15:00 - 15:05
}
