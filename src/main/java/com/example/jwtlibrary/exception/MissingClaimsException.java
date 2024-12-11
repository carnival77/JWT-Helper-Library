package com.example.jwtlibrary.exception;

// 필수 Claim(예: subject)이 누락된 경우 발생
public class MissingClaimsException extends RuntimeException {
    public MissingClaimsException(String message) {
        super(message);
    }
}
