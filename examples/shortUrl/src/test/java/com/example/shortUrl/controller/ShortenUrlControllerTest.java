package com.example.shortUrl.controller;

import com.example.shortUrl.common.exception.NotFoundShortenUrlKeyException;
import com.example.shortUrl.domain.dto.ShortenUrlCreateRequestDto;
import com.example.shortUrl.domain.dto.ShortenUrlCreateResponseDto;
import com.example.shortUrl.domain.repository.ShortenUrlRepository;
import com.example.shortUrl.shortenurlmngt.service.ShortenUrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortenUrlControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ShortenUrlRepository shortenUrlRepository;

    @Autowired
    private ShortenUrlService shortenUrlService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private String local_address = "http://localhost";
    private String path = ":" + port;
    private String url;
    private String local_url;
    private String deployed_url;
    private String params;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("[Controller] 단축URL 생성 테스트")
    @Test
    public void createShortenUrl() throws Exception {

        // given
        url = "/shortenUrl";
        local_url = local_address + path + url;
        // ObjectMapper 추가
        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto("https://www.google.com");
        mvc.perform(post(local_url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(shortenUrlCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        assertThat(shortenUrlRepository.findByOriginalUrl("https://www.google.com")).isNotNull();
        assertThat(shortenUrlRepository.findByOriginalUrl("https://www.google.com").getShortenUrlKey()).hasSize(8);
    }

    @DisplayName("[Controller] 단축URL 정보 조회 테스트")
    @Test
    public void getShortenUrlInfo() throws Exception {

        // given
        url = "/shortenUrl";
        local_url = local_address + path + url;

        // when
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto("https://www.google.com");
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = shortenUrlService.createShortenUrl(shortenUrlCreateRequestDto);
        String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

        mvc.perform(get(local_url + "/" +shortenUrlKey)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        assertThat(shortenUrlRepository.findByShortenUrlKey(shortenUrlKey)).isNotNull();
    }

    @DisplayName("[Controller] 단축URL 리다이렉트 테스트")
    @Test
    public void redirectShortenUrl() throws Exception {

        // given
        local_url = local_address + path;

        // when
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto("https://www.google.com");
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = shortenUrlService.createShortenUrl(shortenUrlCreateRequestDto);
        String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

        mvc.perform(get(local_url + "/" + shortenUrlKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        // then
        assertThat(shortenUrlRepository.findByShortenUrlKey(shortenUrlKey)).isNotNull();
    }

    @DisplayName("[Controller] 단축URL 리다이렉트 시 단축URL 미존재 예외 처리 테스트")
    @Test
    public void redirectShortenUrl_NotFoundShortenUrlKeyException() throws Exception {

        // given
        local_url = local_address + path;
        String shortenUrlKey = "notFoundShortenUrlKey";

        // when & then
        mvc.perform(get(local_url + "/" + shortenUrlKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("[Controller] 단축URL 정보 조회 시 단축URL 미존재 예외 처리 테스트")
    @Test
    public void getShortenUrlInfo_NotFoundShortenUrlKeyException() throws Exception {

        // given
        url = "/shortenUrl";
        local_url = local_address + path + url;
        String shortenUrlKey = "notFoundShortenUrlKey";

        // when & then
        mvc.perform(get(local_url + "/" + shortenUrlKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}
