package com.example.mypet.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalApiService {

    //RestTemplateConfig에서 등록한 RestTemplate 주입
    private final RestTemplate restTemplate;

    //application.properties에 저장해둔 api 키 주입
    //animal-api.service-key=발급받은키(api키 스트링값으로 지정)
    @Value("${animal-api.service-key}")
    private String serviceKey;

    /*
     유기동물 목록 단순 조회
     pageNo, numOfRows만 사용 (페이지번호랑 개수만 받음)
     결과는 JSON 문자열 그대로 반환 */
    public String getAbandonedAnimals(Integer pageNo, Integer numOfRows) {

        //공공데이터포털 v2 엔드포인트 (목록 조회)
        String baseUrl =
        	"https://apis.data.go.kr/1543061/abandonmentPublicService_v2/abandonmentPublic_v2";

        //serviceKey를 URL에 넣기 전에 UTF-8로 인코딩
        //String encodedKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
        String key = serviceKey; //인코딩 없이 properties그대로(오류때문에)
        
        //쿼리 파라미터 조합
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                //.queryParam("serviceKey", encodedKey)
        		.queryParam("serviceKey", key)
                .queryParam("stdt", "20250101") 		//조회시작년도YYYYMMDD형식 일단 임의로 고정값 
                .queryParam("_type", "json")                         //json으로 응답
                .queryParam("pageNo", pageNo != null ? pageNo : 1)   //기본값 1페이지
                .queryParam("numOfRows", numOfRows != null ? numOfRows : 21) //기본값 21개
                .build(false)
                .toUri();
        
        System.out.println("CALL URI = " + uri); //오류때문에확인용

        //공공데이터 api는 특별한 헤더가 필요 없어서 빈 헤더 사용
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        try {
        	//실제 GET 요청 보내고 응답을 String으로 받기
        	ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();

        } catch (RestClientException e) {
            e.printStackTrace();
            return "API 호출 중 오류 발생: " + e.getMessage(); //런타임에러방지
        }
    }

}
