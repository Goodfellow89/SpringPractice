package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
public class GenresController {

    private final GenresService genresService;

    @Autowired
    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

//    @ModelAttribute("genresMap")
//    public Map<String, Map<String, List<Genre>>> getGenres() {
//        return genresService.getGenres();
//    }

    @GetMapping("/genres")
    public String genresPage() {
        return "/genres/index";
    }
}
