package com.example.mypet.controller;

import com.example.mypet.memberDTO.UserDTO;
import com.example.mypet.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class LoginController {

    LoginService service ;

    @Autowired
    public LoginController(LoginService service){
        this.service = service;
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
    @GetMapping(value = "/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping(value = "/adduser")
    public String adduser(UserDTO dto){
        System.out.println("user : " + dto.toString());
        service.adduser(dto);
        return "redirect:/login";
    }

    @PostMapping(value = "/login")
    public String login1(UserDTO dto, HttpSession session){
        UserDTO checkUser = service.checkUser(dto);
        if(checkUser != null){
            session.setAttribute("id", checkUser.getId());
            session.setAttribute("nickname", checkUser.getNickname());
            return "redirect:/";
        }
        else{
            return "redirect:/login";
        }
    }




}
