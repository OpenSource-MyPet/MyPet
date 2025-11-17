package com.example.mypet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mypet.service.AnimalApiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AnimalApiController {

    private final AnimalApiService animalApiService;

    
    //유기동물 목록 조회 테스트용 엔드포인트
    //ex.GET /external/animals/list?pageNo=1&numOfRows=12
    @GetMapping("/external/animals/list")
    public ResponseEntity<String> getAnimals(
            @RequestParam(required = false) Integer pageNo
    ) {
        //서비스 호출 (pageNo 없으면 서비스에서 기본값 1로 처리<안 보내면 null이고 이경우 기본값)
    	String response = animalApiService.getAbandonedAnimals(pageNo, 21); //numofrows는 일단 21로 고정함

        //json 문자열 그대로 반환
        return ResponseEntity.ok(response);
    }
}
