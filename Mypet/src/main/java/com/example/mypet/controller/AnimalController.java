package com.example.mypet.controller;

import com.example.mypet.memberDTO.AnimalDTO;
import com.example.mypet.service.AnimalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AnimalController {

    private AnimalApiService animalApiService;

    @Autowired
    public AnimalController(AnimalApiService service){
        this.animalApiService = service;
    }

    @GetMapping(value = "/animal")
    public String animal(@RequestParam(required = false) Integer pageNo, Model model){
        List<AnimalDTO> animals = animalApiService.getAbandonedAnimalsAsDto(pageNo, 21);
        model.addAttribute("animals", animals);
        return "animals";
    }
    @GetMapping(value = "/animals")
    public String animals(@RequestParam(defaultValue = "1") Integer pageNo, Model model){
        int page = pageNo;
        int total = 2000;
        int totalPage = (int) Math.ceil((double) total/21);
        int limit = 5 ;
        int startpage = ((page-1) / limit) * limit + 1 ;
        int endpage = startpage + limit -1 > totalPage ? totalPage : startpage + limit -1;
        model.addAttribute("start", startpage);
        model.addAttribute("end", endpage);
        model.addAttribute("page", page);
        List<AnimalDTO> animals = animalApiService.getAbandonedAnimalsAsDto(pageNo, 21);
        model.addAttribute("animals", animals);
        return "newFamily";
    }
    @GetMapping(value = "/animals/{id}")
    public String animalDetail(@PathVariable String id, Model model){
        System.out.println("animal id is " + id);
        List<AnimalDTO> list = animalApiService.getSingleAbandonedAnimalsAsDto(id);
        AnimalDTO dto = list.get(0);
        System.out.println(dto.toString());
        model.addAttribute("animal", dto);
        return "animalDetail";
    }
}
