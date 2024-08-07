package org.okten.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMailDto {

    private String subject;

    private String text;

    private String recipient;
}
