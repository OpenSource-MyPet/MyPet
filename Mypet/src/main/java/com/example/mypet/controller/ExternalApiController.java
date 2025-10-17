package com.example.mypet.controller;

import com.example.mypet.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalApiController {
    ExternalApiService service ;

    @Autowired
    public ExternalApiController(ExternalApiService service){
        this.service = service ;
    }
    @GetMapping(value = "/v1/api/laws")
    public ResponseEntity<String> test1(){
        return service.test1();
    }
}
