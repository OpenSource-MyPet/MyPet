package com.example.mypet.controller;

import com.example.mypet.entity.User;
import com.example.mypet.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입 폼
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // templates/signup.html
    }

    //회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {
    	//서버에서 한번 더 중복체크(프론트플래그로 하지만 한 번 더)
        if (userService.isDuplicateLoginId(user.getLoginId())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "signup";
        }
        userService.register(user);
        return "redirect:/user/login";
    }
    
    //아이디 중복확인api (프론트에서 중복확인버튼)
    //사용가능-true 사용불가(이미존재)-false 반환
    @GetMapping("/check-id/{loginId}")
    @ResponseBody
    public boolean checkId(@PathVariable String loginId) {
        boolean isDuplicate = userService.isDuplicateLoginId(loginId);
        return !isDuplicate; // true = 사용 가능, false = 중복
    }

    //로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // templates/login.html
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String loginId,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        return userService.login(loginId, password)
                .map(user -> {
                    session.setAttribute("LOGIN_USER_ID", user.getId());
                    return "redirect:/";   // 로그인 성공 시 메인으로
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
                    return "login";
                });
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
