package com.example.mypet.controller;

import com.example.mypet.service.AiReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AiVetRestController {

    AiReportService service ;

    public AiVetRestController(AiReportService service){
        this.service = service;
    }

    @PostMapping(value = "/report")
    public Map<String, String> report(@RequestBody Map<String, String> request){
        String base64 = request.get("image");
        String symptom = request.get("symptom");
        String type = request.get("type");
        String contentType = request.get("contentType");
        System.out.println("report in restcontroller" + base64 + symptom + type + contentType);
        Map<String, String> response = service.getPrompt(base64, symptom, type, contentType);
//        Map<String, String> response = new HashMap<>();
//        response.put("prompt", prompt);
        return response;
    }
    @PostMapping(value = "/summarize")
    public Map<String, String> summarize(@RequestBody Map<String, String> request, HttpSession session){
        String summary = request.get("summarize");
        System.out.println("summary is " + summary);
        session.setAttribute("summary", summary);
        return Map.of("status", "ok");
    }
}
