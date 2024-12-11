package com.example.shortUrl.domain.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public class ShortenUrlCreateRequestDto {

    @NotNull
    @URL(regexp = "^(https?|ftp):\\/\\/www\\.[A-Za-z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=]+$")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
