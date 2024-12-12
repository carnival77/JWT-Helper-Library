package com.example.shortUrl.domain.dto;

import com.example.shortUrl.domain.ShortenUrl;
import lombok.Getter;

@Getter
public class ShortenUrlInformationDto {

    private final String originalUrl;
    private final String shortenUrlKey;
    private final Long redirectCount;

    public ShortenUrlInformationDto(ShortenUrl shortenUrl) {
        this.originalUrl = shortenUrl.getOriginalUrl();
        this.shortenUrlKey = shortenUrl.getShortenUrlKey();
        this.redirectCount = shortenUrl.getRedirectCount();
    }

}
