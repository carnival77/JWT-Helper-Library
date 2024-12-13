package com.example.shortUrl.shortenurlmngt.controller;

import com.example.shortUrl.domain.dto.ShortenUrlCreateRequestDto;
import com.example.shortUrl.domain.dto.ShortenUrlCreateResponseDto;
import com.example.shortUrl.domain.dto.ShortenUrlInformationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;

@Tag(name = "Shorten URL Management", description = "단축 URL 관리 API")
public interface ShortenUrlControllerDocs {

    @PostMapping("/shortenUrl")
    @Operation(
            summary = "단축 URL 생성 API",
            description =
                    """
                    원본 URL을 입력받아 단축 URL을 생성합니다.
                    
                    * 제약 조건 :
                        * 원본 URL은 필수입니다.
                        * 원본 URL은 유효한 URL 형식이어야 합니다.
                        * 생성 가능한 단축 URL 자원의 수가 최대 '56의 8제곱'을 넘길 수 없습니다.
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "단축 URL 생성 요청",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ShortenUrlCreateRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "단축 URL 생성 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ShortenUrlCreateResponseDto.class)
                            )
                    )
            }
    )
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(@Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) ;

    @GetMapping("{shortenUrlKey}")
    @Operation(
            summary = "단축 URL 리다이렉트 API",
            description =
                    """
                    단축 URL을 입력받아 원본 URL로 리다이렉트합니다. 리다이렉트 횟수를 증가시킵니다.
                    
                    * 응답 성공 시 :
                        * 원본 URL로 리다이렉트
                        * 헤더의 `Location`에 원본 URL 설정
                        * 상태 코드: MOVED_PERMANENTLY(301)
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "301",
                            description = "리다이렉트 성공",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "단축 URL이 존재하지 않음",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<?> redirectShortenUrl(@PathVariable String shortenUrlKey) throws URISyntaxException ;

    @GetMapping("/shortenUrl/{shortenUrlKey}")
    @Operation(
            summary = "단축 URL 정보 조회 API",
            description = "단축 URL을 입력받아 해당 URL의 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "URL 정보 조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ShortenUrlInformationDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "단축 URL이 존재하지 않음",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInfo(@PathVariable String shortenUrlKey);

}
