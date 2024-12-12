package com.example.shortUrl.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShortenUrlCreateResponseDto {

    private String originalUrl;
    private String shortenUrlKey;

}
