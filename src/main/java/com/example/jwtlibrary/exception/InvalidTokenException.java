package com.example.jwtlibrary.exception;

// 서명 불일치, 토큰 형식 불량 등 유효하지 않은 토큰일 때 발생.
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
