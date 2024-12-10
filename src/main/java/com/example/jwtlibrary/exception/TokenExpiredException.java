package com.example.jwtlibrary.exception;

// 만료된 토큰을 사용할 경우 발생
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
