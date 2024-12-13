package com.example.shortUrl.service;

import com.example.shortUrl.common.exception.LackOfShortenUrlKeyException;
import com.example.shortUrl.domain.ShortenUrl;
import com.example.shortUrl.domain.dto.ShortenUrlCreateRequestDto;
import com.example.shortUrl.domain.repository.ShortenUrlRepository;
import com.example.shortUrl.shortenurlmngt.service.ShortenUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ShortenUrlServiceUnitTest {

    @Mock
    private ShortenUrlRepository shortenUrlRepository;

    @InjectMocks
    private ShortenUrlService shortenUrlService;

    @DisplayName("[Service] 단축URL 자원 부족 예외 처리 테스트")
    @Test
    public void LackOfShortenUrlKeyException() {
        // given
        String originalUrl = "https://www.google.com";
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(originalUrl);

        // when
        when(shortenUrlRepository.findByShortenUrlKey(any())).thenReturn(new ShortenUrl(null,null));

        // then
        Assertions.assertThrows(LackOfShortenUrlKeyException.class, () -> {
            shortenUrlService.createShortenUrl(shortenUrlCreateRequestDto);
        });
    }

}
