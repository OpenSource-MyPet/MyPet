package com.example.mypet.controller;

import com.example.mypet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginRestController {

    LoginService service ;

    @Autowired
    public LoginRestController(LoginService service){
        this.service = service;
    }

    @PostMapping(value = "/checkId")
    public Map<String, String> checkId(@RequestBody Map<String, String> body){
        String id = body.get("id");
        System.out.println("checking id is " + id);
        String status = service.checkId(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", status);
        System.out.println("status is " + status);
        return response;
    }


}
