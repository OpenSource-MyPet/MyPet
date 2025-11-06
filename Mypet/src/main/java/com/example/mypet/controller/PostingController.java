package com.example.mypet.controller;

import com.example.mypet.memberDTO.PostingDTO;
import com.example.mypet.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class PostingController {

    PostingService service ;

    @Autowired
    public PostingController(PostingService service){
        this.service = service;
    }

    // 포스팅 페이지
    @GetMapping(value = "/")
    public String index(Model model){
        List<PostingDTO> list = service.getPostings();
        System.out.println("List : " + list);
        model.addAttribute("postings", list);
        return "index";
    }
    // 포스팅 작성 페이지
    @GetMapping(value = "/addpost")
    public String post(){
        return "writePost";
    }
    // 포스팅 작성 POST 요청
    @PostMapping(value = "/addpost")
    public String addpost(PostingDTO dto) throws IOException {
        // session에서 사용자 정보 가져와야함
        String nickname = "익명의 사용자";
        String id = "admin";
        dto.setNickname(nickname);
        dto.setId(id);
        service.savetodb(dto);
        return "redirect:/";
    }
    // 각 포스팅들 상세페이지
    @GetMapping(value = "/postings/{id}")
    public String detail(@PathVariable int id, Model model){
        PostingDTO dto = service.getPosting(id);
        model.addAttribute("detail", dto);
        return "detail" ;
    }

}
