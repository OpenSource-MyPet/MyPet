package com.example.mypet.controller;

import com.example.mypet.memberDTO.CommnetDTO;
import com.example.mypet.memberDTO.LikesDTO;
import com.example.mypet.memberDTO.PostingDTO;
import com.example.mypet.service.InteractionService;
import com.example.mypet.service.PostingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class PostingController {

    PostingService service ;
    InteractionService interactService;

    @Autowired
    public PostingController(PostingService service, InteractionService interactService){
        this.service = service;
        this.interactService = interactService;
    }

    // 포스팅 페이지
    @GetMapping(value = "/")
    public String index(Model model, HttpSession session){
        System.out.println("session id is " + session.getAttribute("id"));

        List<PostingDTO> list = service.getPostings();
        System.out.println("List : " + list);
        model.addAttribute("postings", list);
        if(session.getAttribute("id") != null){
            String id = session.getAttribute("id").toString();
            List<Integer> likeList = interactService.getLikes(id);
            System.out.println("Like is " + likeList.toString());
            model.addAttribute("Likes", likeList);

            List<Integer> BookmarkList = interactService.getBookMarks(id);
            model.addAttribute("BookMarks", BookmarkList);
        }

        return "index";
    }
    // 포스팅 작성 페이지
    @GetMapping(value = "/addpost")
    public String post(HttpSession session, RedirectAttributes redirectAttributes){
        if(session.getAttribute("id") == null){
            redirectAttributes.addFlashAttribute("alertMsg", "로그인이 필요합니다!");
            return "redirect:/login";
        }

        return "writePost";


    }
    // 포스팅 작성 POST 요청
    @PostMapping(value = "/addpost")
    public String addpost(PostingDTO dto, HttpSession session) throws IOException {
        // session에서 사용자 정보 가져와야함
        String id = session.getAttribute("id").toString();
        String nickname = session.getAttribute("nickname").toString();

        // String nickname = "익명의 사용자";
        // String id = "admin";
        dto.setNickname(nickname);
        dto.setId(id);
        service.savetodb(dto);
        return "redirect:/";
    }
    // 각 포스팅들 상세페이지
    @GetMapping(value = "/postings/{id}")
    public String detail(@PathVariable int id, Model model, HttpSession session){
        PostingDTO dto = service.getPosting(id);
        model.addAttribute("detail", dto);
        if(session.getAttribute("id") != null){
            String userId = session.getAttribute("id").toString();
            List<Integer> likeList = interactService.getLikes(userId);
            System.out.println("Like is " + likeList.toString());
            model.addAttribute("Likes", likeList);

            List<Integer> BookmarkList = interactService.getBookMarks(userId);
            model.addAttribute("BookMarks", BookmarkList);
        }
        List<CommnetDTO> commentsList = interactService.getComments(id);
        model.addAttribute("comments", commentsList);
        return "detail" ;
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
