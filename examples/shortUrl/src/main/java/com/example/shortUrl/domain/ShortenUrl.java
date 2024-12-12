package com.example.shortUrl.domain;

import lombok.Getter;

import java.util.Random;

@Getter
public class ShortenUrl {

    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;

    public ShortenUrl(String originalUrl, String shortenUrlKey) {
        this.originalUrl = originalUrl;
        this.shortenUrlKey = shortenUrlKey;
        this.redirectCount = 0L;
    }

    public static String generateShortenUrlKey() {

        String base56Characters = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        Random random = new Random();
        StringBuilder shortenUrlKey = new StringBuilder();

        for(int i=0;i<8;i++){
            shortenUrlKey.append(base56Characters.charAt(random.nextInt(0,base56Characters.length())));
        }

        return shortenUrlKey.toString();
    }

    public void increaseRedirectCount() {
        // 리다이렉트 횟수 증가
        this.redirectCount++;
    }

}
