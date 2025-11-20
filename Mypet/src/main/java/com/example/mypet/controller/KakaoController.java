package com.example.mypet.controller;

import com.example.mypet.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoController {

    KakaoService service;

    @Autowired
    public KakaoController(KakaoService service){
        this.service = service;
    }

    @GetMapping(value = "/kakao")
    public String kakao(Model model){
        String key = "5a6aee031958aee23852152d89381f27";
        String callback = "http://localhost:8080/kakao/callback";
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+key+"&redirect_uri="+callback+"&prompt=login";
        model.addAttribute("url", url);
        return "kakao";
    }

    @GetMapping(value = "/kakao/callback")
    public String callback(@RequestParam() String code, HttpSession session){
        System.out.println("code : " + code);
        String token = service.getToken(code);
        System.out.println("token : " +token);
        session.setAttribute("access_token", token);
        return "index";
    }

    @GetMapping(value = "/kakao/logout")
    public String kakaoLogout(HttpSession session){
        if(session.getAttribute("access_token") != null){
            System.out.println("logout success");
            String token = session.getAttribute("access_token").toString();
//            service.logout(token);
//            service.unlink(token);

            session.invalidate();
        }
        String key = "5a6aee031958aee23852152d89381f27";
        String callback = "http://localhost:8080/kakao/logout/callback";
        String logout = "https://kauth.kakao.com/oauth/logout?client_id="+key+"&logout_redirect_uri="+callback;
        return "redirect:" + logout;
    }
    @GetMapping(value = "/kakao/logout/callback")
    public String logoutCallback(){
        return "redirect:/";
    }
}
