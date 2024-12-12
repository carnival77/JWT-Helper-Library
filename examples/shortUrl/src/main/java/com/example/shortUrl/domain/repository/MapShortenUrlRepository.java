package com.example.shortUrl.domain.repository;

import com.example.shortUrl.domain.ShortenUrl;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapShortenUrlRepository implements ShortenUrlRepository {

    private Map<String, ShortenUrl> shortenUrlMap = new ConcurrentHashMap<>();

    @Override
    public void save(ShortenUrl shortenUrl) {
        // 단축 URL 정보를 저장하는 로직
        shortenUrlMap.put(shortenUrl.getShortenUrlKey(), shortenUrl);
    }

    @Override
    public ShortenUrl findByOriginalUrl(String originalUrl) {

        for (ShortenUrl shortenUrl : shortenUrlMap.values()) {
            if (shortenUrl.getOriginalUrl().equals(originalUrl)) {
                return shortenUrl;
            }
        }

        return null;
    }

    @Override
    public ShortenUrl findByShortenUrlKey(String shortenUrlKey) {

        for (String key : shortenUrlMap.keySet()) {
            if (shortenUrlKey.equals(key)) {
                return shortenUrlMap.get(key);
            }
        }

        return null;
    }
}
