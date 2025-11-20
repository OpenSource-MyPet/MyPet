package com.mysite.sbb.service;

import com.mysite.sbb.dto.HospitalDto;
import com.mysite.sbb.dto.HospitalListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class HospitalSearchService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${mepet.api.kakao-rest-key}")
    private String kakaoRestKey;              // REST API 키

    @Value("${mepet.api.search-radius:3000}")
    private int defaultRadius;                // 0~20000

    // 동물병원만 거르기(보조 필터)
    private static final Pattern ANIMAL_ONLY = Pattern.compile(
            "동물|수의|애견|펫|반려|동물메디컬|동물의료|동물병원",
            Pattern.CASE_INSENSITIVE
    );

    public HospitalListResponse searchNearby(double lat, double lng, Integer radiusMeters) {
        int radius = (radiusMeters == null || radiusMeters <= 0) ? defaultRadius : radiusMeters;
        if (radius > 20000) radius = 20000;

        //  1) 키워드 검색: '동물병원' (한글 → UTF-8 인코딩 필요)
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://dapi.kakao.com/v2/local/search/keyword.json")
                .queryParam("query", "동물병원")
                .queryParam("y", lat)          // 위도
                .queryParam("x", lng)          // 경도
                .queryParam("radius", radius)  // 0~20000m
                .encode(StandardCharsets.UTF_8) // ★ 핵심: 한글 파라미터 인코딩
                .build()
                .toUri();

        // (참고) 카테고리 검색(HP8=병원)로 바꾸면 한글 쿼리 불필요
        // URI uri = UriComponentsBuilder
        //         .fromHttpUrl("https://dapi.kakao.com/v2/local/search/category.json")
        //         .queryParam("category_group_code", "HP8")
        //         .queryParam("y", lat)
        //         .queryParam("x", lng)
        //         .queryParam("radius", radius)
        //         .build()
        //         .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestKey);

        System.out.println("[KAKAO REQ] " + uri);
        try {
            ResponseEntity<Map> res = restTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(headers), Map.class);

            System.out.println("[KAKAO RES] status=" + res.getStatusCode());

            if (!res.getStatusCode().is2xxSuccessful()) {
                System.out.println("[KAKAO BODY] " + res.getBody());
                return new HospitalListResponse(Collections.emptyList());
            }

            Map body = res.getBody();
            if (body == null) return new HospitalListResponse(Collections.emptyList());

            List<Map<String, Object>> docs = (List<Map<String, Object>>) body.get("documents");
            if (docs == null) docs = Collections.emptyList();

            // 보조 필터: 이름/카테고리에 동물 관련 키워드 포함만 남기기
            docs = docs.stream().filter(d -> {
                String name = String.valueOf(d.getOrDefault("place_name", ""));
                String cat  = String.valueOf(d.getOrDefault("category_name", ""));
                return ANIMAL_ONLY.matcher(name).find() || ANIMAL_ONLY.matcher(cat).find();
            }).collect(Collectors.toList());

            List<HospitalDto> items = docs.stream().map(d -> new HospitalDto(
                    String.valueOf(d.getOrDefault("place_name", "")),
                    String.valueOf(d.getOrDefault("road_address_name",
                            d.getOrDefault("address_name",""))),
                    String.valueOf(d.getOrDefault("phone","")),
                    toDouble(d.get("y")), // 위도
                    toDouble(d.get("x"))  // 경도
            )).collect(Collectors.toList());

            return new HospitalListResponse(items);

        } catch (RestClientResponseException e) {
            System.err.println("[KAKAO HTTP ERR] status=" + e.getRawStatusCode());
            System.err.println("[KAKAO HTTP BODY] " + e.getResponseBodyAsString());
            return new HospitalListResponse(Collections.emptyList());
        } catch (Exception e) {
            e.printStackTrace();
            return new HospitalListResponse(Collections.emptyList());
        }
    }

    private static double toDouble(Object v) {
        if (v == null) return 0d;
        try { return Double.parseDouble(String.valueOf(v)); }
        catch (Exception e) { return 0d; }
    }
}
