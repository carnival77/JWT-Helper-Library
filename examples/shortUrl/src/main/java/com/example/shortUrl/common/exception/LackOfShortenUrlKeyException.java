package com.example.shortUrl.common.exception;

public class LackOfShortenUrlKeyException extends RuntimeException {
    public LackOfShortenUrlKeyException() {
        super("단축 URL 자원이 부족합니다.");
    }
}
