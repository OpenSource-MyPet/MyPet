package com.example.mypet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;


@Service
public class KakaoService {
    public String getToken(String code) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", "5a6aee031958aee23852152d89381f27")
                .queryParam("redirect_uri", "http://localhost:8080/kakao/callback")
                .queryParam("code", code)
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);
        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> response = rest.exchange(uri, HttpMethod.POST, entity, String.class);
        String json = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        String access_token = null ;
        try{
            Map<String, String> resMap = mapper.readValue(json, Map.class);
            access_token = resMap.get("access_token");
            sendMessage(access_token);
        }catch(Exception e){
            e.printStackTrace();
        }



        return response.getBody();
    }

    private void sendMessage(String accessToken) {
        String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.setBearerAuth(accessToken);
        String templateData = """
    {
        "object_type": "text",
        "text": "텍스트 영역입니다. 최대 200자 표시 가능합니다.",
        "link": {
            "web_url": "https://developers.kakao.com",
            "mobile_web_url": "https://developers.kakao.com"
        },
        "button_title": "바로 확인"
    }
    """;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("template_object", templateData);

        HttpEntity<MultiValueMap> entity = new HttpEntity<>(params, headers);

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.postForEntity(url, entity, String.class);
        System.out.println("Kakao response : " + response.getBody());

    }

    public void logout(String token) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com/v1/user/logout")
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.exchange(uri, HttpMethod.POST, entity, String.class);
        System.out.println("Kakao logout : "+ response.getBody());
    }

    public void unlink(String token) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com/v1/user/unlink")
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.exchange(uri, HttpMethod.POST, entity, String.class);
        System.out.println("Kakao logout : "+ response.getBody());
    }
}
