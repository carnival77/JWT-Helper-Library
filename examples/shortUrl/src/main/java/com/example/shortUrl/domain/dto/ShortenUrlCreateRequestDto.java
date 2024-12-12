package com.example.shortUrl.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ShortenUrlCreateRequestDto {

    @NotNull
    @URL(regexp = "^(https?|ftp):\\/\\/www\\.[A-Za-z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=]+$")
    private String originalUrl;
}
