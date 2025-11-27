// map 요청 들어오면 templates/map.html을 반환하는 가장 기본적인 뷰 컨트롤러 
package com.mysite.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @GetMapping("/map")
    public String mapPage() {
        return "map"; // templates/map.html
    }
}

