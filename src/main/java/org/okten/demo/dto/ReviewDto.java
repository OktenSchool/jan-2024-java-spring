package org.okten.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDto {

    @NotBlank
    private String text;

    @Min(0)
    @Max(10)
    private Integer rating;

    private LocalDateTime timestamp;
}
