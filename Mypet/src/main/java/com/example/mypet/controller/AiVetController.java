package com.example.mypet.controller;

import com.example.mypet.memberDTO.AIVetFormDTO;
import com.example.mypet.service.AiReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AiVetController {

    AiReportService service ;

    public AiVetController(AiReportService service){
        this.service = service;
    }

    @GetMapping(value = "/aiVet")
    public String aivet(){
        return "aiVetForm";
    }

    @PostMapping(value = "/aiVet")
    public String aivetform(AIVetFormDTO dto, Model model, RedirectAttributes redirectAttributes){
        if(dto.getImage().isEmpty()){
            System.out.println("image is nullsssss");
        }
        else{
            System.out.println("image is here");
        }
        String contentType = dto.getImage().getContentType();
        if(contentType == null || contentType.equals("application/octet-stream")){
            System.out.println("content type is changed " + contentType + " to " + "image/png");
            contentType = "image/png";
        }
        dto.setContentType(contentType);
        String base64 = service.getBase64(dto.getImage());
        System.out.println("bse : "+ base64);
        dto.setBase64(base64);

        model.addAttribute("form", dto);

        return "aiVetReport";
    }

//    @GetMapping(value = "/aiVetReport")
//    public String aireport(AIVetFormDTO dto, Model model){
//        System.out.println("ai dto is in report " + dto.toString());
//        if(dto.getImage().isEmpty()){
//            System.out.println("image is null");
//        }
//        String contentType = dto.getImage().getContentType();
//        if(contentType == null || contentType.equals("application/octet-stream")){
//            System.out.println("content type is changed " + contentType + " to " + "image/png");
//            contentType = "image/png";
//        }
//        dto.setContentType(contentType);
//        String base64 = service.getBase64(dto.getImage());
//        System.out.println("bse : "+ base64);
//        dto.setBase64(base64);
//
//        model.addAttribute("form", dto);
//        return "aiVetReport";
//
//    }
}
