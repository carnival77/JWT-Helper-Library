package com.example.shortUrl.service;

import com.example.shortUrl.common.exception.LackOfShortenUrlKeyException;
import com.example.shortUrl.common.exception.NotFoundShortenUrlKeyException;
import com.example.shortUrl.domain.ShortenUrl;
import com.example.shortUrl.domain.dto.ShortenUrlCreateRequestDto;
import com.example.shortUrl.domain.repository.ShortenUrlRepository;
import com.example.shortUrl.shortenurlmngt.service.ShortenUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShortenUrlServiceTest {

    @Autowired
    private ShortenUrlRepository shortenUrlRepository;

    @Autowired
    private ShortenUrlService shortenUrlService;

    @DisplayName("[Service] 단축URL 생성 후 단축URL을 통한 원래 URL 조회 테스트")
    @Test
    public void createShortenUrlAndFindOriginalUrl() {
        // given
        String originalUrl = "https://www.google.com";

        // when
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(originalUrl);
        String shortenUrlKey = shortenUrlService.createShortenUrl(shortenUrlCreateRequestDto).getShortenUrlKey();
        String foundOriginalUrl = shortenUrlService.getOriginalUrl(shortenUrlKey);

        // then
        Assertions.assertEquals(originalUrl, foundOriginalUrl);
    }

    @DisplayName("[Service] 단축URL 조회 불가 예외 처리 테스트")
    @Test
    public void NotFoundShortenUrlKeyException() {
        // given
        String shortenUrlKey = "notFoundShortenUrlKey";

        // when & then
        Assertions.assertThrows(NotFoundShortenUrlKeyException.class, () -> {
            shortenUrlService.getShortenUrlInfo(shortenUrlKey);
            shortenUrlService.getOriginalUrl(shortenUrlKey);
        });
    }

}
