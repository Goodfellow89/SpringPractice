package com.example.BookShopApp.controller;

import com.example.BookShopApp.data.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAuthors(Model model) {
        model.addAttribute("authorLists", authorService.getAuthors());
        model.addAttribute("letters", authorService.getLetters());
        return "authors/index";
    }
}
