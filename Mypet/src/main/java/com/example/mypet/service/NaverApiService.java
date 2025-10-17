package com.example.mypet.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;

@Service
public class NaverApiService {
    public String search(String _query){
        String query = _query ;
        String enquery = null ;
        try{
            enquery = URLEncoder.encode(query, "UTF-8");
        }catch(Exception e){
            e.getMessage();
        }
        System.out.println("query : " + enquery);
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com/v1/search/local.xml")
                .queryParam("query", query)
                .encode()
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders() ;
        headers.set("X-Naver-Client-Id", "u8ZUWLpoHZS71x63cEKo");
        headers.set("X-Naver-Client-Secret", "ASvwzXW_31");

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.exchange(uri, HttpMethod.GET, entity, String.class);
        return response.getBody() ;
    }
}
