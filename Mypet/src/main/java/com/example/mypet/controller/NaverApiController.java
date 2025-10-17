package com.example.mypet.controller;

import com.example.mypet.service.NaverApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaverApiController {
    NaverApiService service ;

    @Autowired
    public NaverApiController(NaverApiService service){
        this.service = service;
    }

    @PostMapping(value = "/v1/api/naver")
    public String search(@RequestParam String searchQuery){
        return service.search(searchQuery);
    }

}
