package com.example.shortUrl.domain.dto;

import lombok.Getter;

@Getter
public class ShortenUrlInformationDto {

    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;

}
