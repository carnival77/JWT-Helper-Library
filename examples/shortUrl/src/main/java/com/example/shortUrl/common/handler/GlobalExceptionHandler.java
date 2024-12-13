package com.example.shortUrl.common.handler;

import com.example.shortUrl.common.exception.NotFoundShortenUrlKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundShortenUrlKeyException.class)
    public ResponseEntity<?> handleNotFoundShortenUrlKeyException(NotFoundShortenUrlKeyException e) {
        log.error("NotFoundShortenUrlKeyException: {}", e.getMessage());
        return new ResponseEntity<String>("단축 URL을 찾지 못했습니다.", HttpStatus.NOT_FOUND);
    }
}
