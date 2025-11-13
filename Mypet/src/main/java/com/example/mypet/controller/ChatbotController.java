package com.example.mypet.controller;

import com.example.mypet.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatbotController {
    ChatbotService service ;

    @Autowired
    public ChatbotController(ChatbotService service){
        this.service = service;
    }

    @PostMapping(value = "/gpt")
    public Map<String, String> gpt(@RequestBody Map<String, String> body){
        String text = body.get("prompt").toString();
        System.out.println("user prompt is " + text);
        try {
            String prompt = service.getprompt(text);
            Map<String, String> response = new HashMap<>();
            response.put("status", "ok");
            response.put("prompt", prompt);
            return response;

        }catch (Exception e){
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("status", "no");
            response.put("prompt", null);
            return response;
        }

    }

}
