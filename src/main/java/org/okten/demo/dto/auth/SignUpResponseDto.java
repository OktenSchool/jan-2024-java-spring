package org.okten.demo.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class SignUpResponseDto {

    private Long id;

    private String username;

    private OffsetDateTime registeredAt;
}
