package com.example.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gpt/*")
public class OpenAiController {
    @Value("${gpt.api}")
    private String apiKey;
    private String endPoint = "https://api.openai.com/v1/chat/completions";

    @PostMapping("/question")
    public Mono<Map> question(@RequestBody Map<String, String> body){
        String content = body.get("content");

        Map<String, Object> setting = new HashMap<>();
        setting.put("role", "system");
        setting.put("content", "너는 우리 사이트의 고객센터 직원이야. 우리 웹사이트에 대해 뭐든 답변해줄 수 있어!" +
                "답변할 수 없는 내용은 회사 전화번호로 연락하라고 알려줘야 해." +
                "우리 회사 전화번호는 02-1234-1234이고, 고객이 물어보면 전화번호를 알려줘야 해." +
                "사용자가 불편하면 위로해줘야 해.");
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", content);

        List<Map> messages = List.of(setting, message);

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("model", "gpt-3.5-turbo");
        reqBody.put("messages", messages);
        reqBody.put("temperature", 0.8);
        reqBody.put("max_tokens", 1000);

        WebClient webClient = WebClient.builder()
                .baseUrl(endPoint) //요청을 보낼 URL
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // header 설정
                .defaultHeader("Authorization", "Bearer " + apiKey).build();

        Mono<Map> resultBody = webClient.post() // 요청 방식을 설정한다
                .contentType(MediaType.APPLICATION_JSON) // 보내는 데이터의 타입
                .body(BodyInserters.fromValue(reqBody)) // body 설정, fromValue로 데이터를 담는다
                .retrieve() // 위에서 만든 요청을 보낸다
                .bodyToMono(Map.class); // 응답의 body를 받는다, Mono<Map>으로 받는다.

        return resultBody;
    }
}
