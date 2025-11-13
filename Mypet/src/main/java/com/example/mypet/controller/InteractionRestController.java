package com.example.mypet.controller;

import com.example.mypet.service.InteractionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class InteractionRestController {
    InteractionService service ;

    @Autowired
    public InteractionRestController(InteractionService service){
        this.service = service;
    }

    @PostMapping(value = "/like")
    public Map<String, String> like(@RequestBody Map<String, Integer> body, HttpSession session){
        Object obj_id = session.getAttribute("id");
        if(obj_id == null){
            Map<String, String> response = new HashMap<>();
            response.put("status", "no");
            return response;
        }
        String id = obj_id.toString();
        int isLiked = body.get("status");
        int rid = body.get("post_id");
        System.out.println("like status is " + isLiked);
        service.saveLike(rid,id, isLiked);
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        return response;
    }

    @PostMapping(value = "/bookmark")
    public Map<String, String> bookmark(@RequestBody Map<String, Integer> body, HttpSession session){
        Object obj_id = session.getAttribute("id");
        if(obj_id == null){
            Map<String, String> response = new HashMap<>();
            response.put("status", "no");
            return response;
        }
        String id = obj_id.toString();
        int isMarked = body.get("status");
        int rid = body.get("post_id");
        service.saveMark(rid, id, isMarked);
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        return response;
    }

    @PostMapping(value = "/comments")
    public Map<String, String> comments(@RequestBody Map<String, Object> body, HttpSession session){
        Object obj_id = session.getAttribute("id");
        Object obj_nickname = session.getAttribute("nickname");
        if(obj_id == null){
            Map<String, String> response = new HashMap<>();
            response.put("status", "no");
            return response;
        }
        String userId = obj_id.toString();
        String nickname = obj_nickname.toString();
        String comments = (String) body.get("comments");
        int postId = (int) body.get("postId");
        service.saveComments(comments, postId, userId, nickname);
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        return response;
    }
}
