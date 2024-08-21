package org.okten.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.okten.demo.entity.ProductAvailability;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAvailabilityUpdatedEvent {

    private Long productId;

    private ProductAvailability availability;
}
