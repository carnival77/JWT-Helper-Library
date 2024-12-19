package com.example.shortUrl.common.exception;

public class NotFoundShortenUrlKeyException extends RuntimeException {
    public NotFoundShortenUrlKeyException() {
        super("해당 단축 URL 키를 찾을 수 없습니다.");
    }
}
