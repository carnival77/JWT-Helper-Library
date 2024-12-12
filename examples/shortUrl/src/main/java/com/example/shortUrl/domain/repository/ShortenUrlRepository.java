package com.example.shortUrl.domain.repository;

import com.example.shortUrl.domain.ShortenUrl;

public interface ShortenUrlRepository {

    public void save(ShortenUrl shortenUrl);

    public ShortenUrl findByOriginalUrl(String originalUrl);

    public ShortenUrl findByShortenUrlKey(String shortenUrlKey);
}
