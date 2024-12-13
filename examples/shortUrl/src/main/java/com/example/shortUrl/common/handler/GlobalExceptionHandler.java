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
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LayerInstantiationException.class)
    public ResponseEntity<?> handle(LayerInstantiationException e) {
        log.error("LayerInstantiationException: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
