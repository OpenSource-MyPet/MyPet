package com.example.mypet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mypet.service.AnimalApiService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import com.example.mypet.memberDTO.AnimalDTO;


@RestController
@RequiredArgsConstructor
public class AnimalApiController {

    private final AnimalApiService animalApiService;

    
    //유기동물 목록 조회 테스트용 엔드포인트
    //ex.GET /external/animals/list?pageNo=1&numOfRows=21
    @GetMapping("/external/animals/list")
    public ResponseEntity<String> getAnimals(
            @RequestParam(required = false) Integer pageNo
    ) {
        //서비스 호출 (pageNo 없으면 서비스에서 기본값 1로 처리<안 보내면 null이고 이경우 기본값)
    	String response = animalApiService.getAbandonedAnimals(pageNo, 21); //numofrows는 일단 21로 고정함

        //json 문자열 그대로 반환
        return ResponseEntity.ok(response);
    }


    //dto 기반 유기동물 목록 조회 (파싱된 결과 반환)
    //ex) GET /external/animals/list/dto?pageNo=1
    @GetMapping("/external/animals/list/dto")
    public ResponseEntity<List<AnimalDTO>> getAnimalsAsDto(
            @RequestParam(required = false) Integer pageNo
    ) {
        //Service에서 JSON → List<AnimalDTO> 변환한 메서드 호출
        List<AnimalDTO> animals = animalApiService.getAbandonedAnimalsAsDto(pageNo, 21);

        //@RestController + ResponseEntity<List<AnimalDTO>>
        //=> 자동으로 JSON 배열로 변환되어 응답됨
        return ResponseEntity.ok(animals);
    }

}
