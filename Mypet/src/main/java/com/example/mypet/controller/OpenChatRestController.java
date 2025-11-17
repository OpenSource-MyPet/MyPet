package com.example.mypet.controller;

import com.example.mypet.repository.ChatRepository;
import com.example.mypet.service.OpenChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OpenChatRestController {

    OpenChatService service ;

    @Autowired
    public OpenChatRestController(OpenChatService service){
        this.service = service;
    }

    @PostMapping(value = "/addChatUserCount")
    public Map<String, String> chatcountUp(@RequestBody Map<String, Integer> body){
        int roomId = body.get("roomId") ;
        System.out.println("roomid is " + roomId);
        service.chatCountup(roomId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        return response;
    }
}
