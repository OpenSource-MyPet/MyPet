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

    // 외부 API(Kakao Local API)를 호출하기 위해 RestTemplate 사용
    private final RestTemplate restTemplate = new RestTemplate();

    // application.properties에서 Kakao REST API 키 가져오기
    @Value("${mepet.api.kakao-rest-key}")
    private String kakaoRestKey;

    // 기본 검색 반경 (기본값=3000m)
    @Value("${mepet.api.search-radius:3000}")
    private int defaultRadius;

    // Kakao API는 병원(HP8) 범위가 너무 넓어서 사람 병원도 섞여 있음
    // 그래서 이름이나 카테고리명에 “동물, 애견, 펫” 같은 단어가 들어가는지 체크해서 동물병원만 남김
    private static final Pattern ANIMAL_ONLY = Pattern.compile(
            "동물|수의|애견|펫|반려|동물메디컬|동물의료|동물병원",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * 프론트에서 준 위도(lat), 경도(lng), 반경(radius) 기준으로
     * 주변 동물병원을 Kakao API에 요청해서 리스트로 돌려주는 메소드
     */
    public HospitalListResponse searchNearby(double lat, double lng, Integer radiusMeters) {

        // radius가 없으면 기본 반경 사용 / 최대 반경은 Kakao 정책에 따라 20km
        int radius = (radiusMeters == null || radiusMeters <= 0) ? defaultRadius : radiusMeters;
        if (radius > 20000) radius = 20000;

        // Kakao Local API - “동물병원” 키워드 검색 요청 URL 만들기
        // query=동물병원 / x=경도 / y=위도 / 반경=radius
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://dapi.kakao.com/v2/local/search/keyword.json")
                .queryParam("query", "동물병원")
                .queryParam("y", lat)
                .queryParam("x", lng)
                .queryParam("radius", radius)
                .encode(StandardCharsets.UTF_8) // 한글 검색어를 안전하게 UTF-8로 인코딩
                .build()
                .toUri();

        // HTTP 요청 헤더에 Kakao 인증키 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestKey);

        try {
            // Kakao API GET 요청 보내기
            ResponseEntity<Map> res = restTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(headers), Map.class);

            // 정상 응답이 아니면 빈 리스트 반환
            if (!res.getStatusCode().is2xxSuccessful()) {
                return new HospitalListResponse(Collections.emptyList());
            }

            Map body = res.getBody();
            if (body == null) return new HospitalListResponse(Collections.emptyList());

            // Kakao 응답에서 documents 배열 꺼내기
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> docs = (List<Map<String, Object>>) body.get("documents");
            if (docs == null) docs = Collections.emptyList();

            // Kakao API는 사람 병원까지 함께 내려오기 때문에
            // 이름(place_name)이나 카테고리(category_name)에 동물 관련 단어가 들어간 것만 추림
            docs = docs.stream().filter(d -> {
                String name = String.valueOf(d.getOrDefault("place_name", ""));
                String cat  = String.valueOf(d.getOrDefault("category_name", ""));
                return ANIMAL_ONLY.matcher(name).find() || ANIMAL_ONLY.matcher(cat).find();
            }).collect(Collectors.toList());

            // 우리가 쓰기 편한 DTO 형태로 변환 (name, address, phone, lat, lng)
            List<HospitalDto> items = docs.stream().map(d -> new HospitalDto(
                    String.valueOf(d.getOrDefault("place_name", "")),
                    String.valueOf(d.getOrDefault("road_address_name",
                            d.getOrDefault("address_name",""))),
                    String.valueOf(d.getOrDefault("phone","")),
                    toDouble(d.get("y")), // 위도
                    toDouble(d.get("x"))  // 경도
            )).collect(Collectors.toList());

            // 전체 리스트를 HospitalListResponse로 감싸서 반환
            return new HospitalListResponse(items);

        } catch (RestClientResponseException e) {
            // Kakao API 에러 (key 오류, 잘못된 요청 등)
            return new HospitalListResponse(Collections.emptyList());
        } catch (Exception e) {
            // 기타 예외
            e.printStackTrace();
            return new HospitalListResponse(Collections.emptyList());
        }
    }

    // 문자열/객체 형태로 들어온 값을 안전하게 double로 변환
    private static double toDouble(Object v) {
        if (v == null) return 0d;
        try { return Double.parseDouble(String.valueOf(v)); }
        catch (Exception e) { return 0d; }
    }
}
