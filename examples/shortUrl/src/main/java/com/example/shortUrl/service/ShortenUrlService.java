package com.example.shortUrl.service;

import com.example.shortUrl.domain.ShortenUrl;
import com.example.shortUrl.domain.repository.ShortenUrlRepository;
import com.example.shortUrl.domain.dto.ShortenUrlCreateRequestDto;
import com.example.shortUrl.domain.dto.ShortenUrlCreateResponseDto;
import com.example.shortUrl.domain.dto.ShortenUrlInformationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;

    public ShortenUrlCreateResponseDto createShortenUrl(ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {

        String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();

        // 단축 URL 키 생성
        String shortenUrlKey = ShortenUrl.generateShortenUrlKey();

        // 원래의 URL과 단축 URL 키를 통해 ShortenUrl 도메인 객체 생성
        ShortenUrl shortenUrl = new ShortenUrl(originalUrl,shortenUrlKey);

        // 생성된 ShortenUrl 도메인 객체를 저장
        shortenUrlRepository.save(shortenUrl);

        // ShortenUrl 을 ShortenUrlCreateResponseDto 로 변환
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(shortenUrl);

        return shortenUrlCreateResponseDto;
    }

    public ShortenUrlInformationDto getShortenUrlInfo(String shortenUrlKey) {

        // 단축 URL 키를 통해 ShortenUrl 도메인 객체 조회
        ShortenUrl shortenUrl = shortenUrlRepository.findByShortenUrlKey(shortenUrlKey);

        // ShortenUrl 도메인 객체를 ShortenUrlInformationDto 로 변환
        ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);

        return shortenUrlInformationDto;
    }

}
