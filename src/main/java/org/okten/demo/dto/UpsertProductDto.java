package org.okten.demo.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.okten.demo.entity.ProductAvailability;

// upsert = update + insert
@Data
public class UpsertProductDto {

    @NotBlank
    private String name;

    @DecimalMin("0")
    @DecimalMax("1900")
    private double price;

    private ProductAvailability availability;
}
