package com.example.shortUrl.service;

import com.example.shortUrl.common.exception.NotFoundShortenUrlKeyException;
import com.example.shortUrl.domain.repository.ShortenUrlRepository;
import com.example.shortUrl.shortenurlmngt.service.ShortenUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShortenUrlServiceTest {

    @Autowired
    private ShortenUrlRepository shortenUrlRepository;

    @Autowired
    private ShortenUrlService shortenUrlService;

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
