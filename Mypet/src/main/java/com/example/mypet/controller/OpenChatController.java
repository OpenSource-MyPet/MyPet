package com.example.mypet.controller;

import com.example.mypet.entity.ChatEntity;
import com.example.mypet.memberDTO.ChatDTO;
import com.example.mypet.service.OpenChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OpenChatController {

    OpenChatService service ;

    @Autowired
    public OpenChatController(OpenChatService service){
        this.service = service;
    }


    @GetMapping(value = "/openchat")
    public String openchat(Model model){
        List<ChatDTO> list = service.getChatList();
        model.addAttribute("chatList", list);
        return "chatList";
    }
    @GetMapping(value = "/addchat")
    public String addchat(HttpSession session, RedirectAttributes redirectAttributes){
        if(session.getAttribute("id") == null){
            redirectAttributes.addFlashAttribute("alertMsg", "로그인이 필요합니다!");
            return "redirect:/login";
        }
        return "writeChat";
    }

    @PostMapping(value = "/addchat")
    public String addchatList(ChatDTO dto, HttpSession session){
        String id = session.getAttribute("id").toString();
        String nickname = session.getAttribute("nickname").toString();
        dto.setUserId(id);
        dto.setNickname(nickname);
        service.savetodb(dto);
        return "redirect:/openchat";
    }

    @GetMapping(value = "/openchat/{roomid}")
    public String openchattingRoom(@PathVariable int roomid, HttpSession session, RedirectAttributes redirectAttributes, Model model){
        if(session.getAttribute("id") == null){
            redirectAttributes.addFlashAttribute("alertMsg", "로그인이 필요합니다!");
            return "redirect:/login";
        }
        ChatDTO dto = service.getChatRoom(roomid);
        model.addAttribute("chatRoom", dto);
        String id = session.getAttribute("id").toString();
        String nickname = session.getAttribute("nickname").toString();
        Map<String, String> user = new HashMap<>();
        user.put("id", id);
        user.put("nickname", nickname);
        model.addAttribute("user", user);
        return "chattingRoom";

    }
}
