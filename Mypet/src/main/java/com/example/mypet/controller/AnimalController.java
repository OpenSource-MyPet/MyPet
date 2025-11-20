package com.example.mypet.controller;

import com.example.mypet.memberDTO.AnimalDTO;
import com.example.mypet.service.AnimalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
