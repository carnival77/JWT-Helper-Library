package com.example.shortUrl.shortenurlmngt.controller;

import com.example.shortUrl.domain.dto.ShortenUrlCreateRequestDto;
import com.example.shortUrl.domain.dto.ShortenUrlCreateResponseDto;
import com.example.shortUrl.domain.dto.ShortenUrlInformationDto;
import com.example.shortUrl.shortenurlmngt.service.ShortenUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShortenUrlController implements ShortenUrlControllerDocs {

    private final ShortenUrlService shortenUrlService;

    @Override
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(@Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {
        return ResponseEntity.ok(shortenUrlService.createShortenUrl(shortenUrlCreateRequestDto));
    }

    @Override
    public ResponseEntity<?> redirectShortenUrl(@PathVariable String shortenUrlKey) throws URISyntaxException {

        String originalUrl = shortenUrlService.getOriginalUrl(shortenUrlKey);

        URI redirectUrl = new URI(originalUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUrl);

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @Override
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInfo(@PathVariable String shortenUrlKey) {
        return ResponseEntity.ok(shortenUrlService.getShortenUrlInfo(shortenUrlKey));
    }

}
