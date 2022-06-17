package com.example.MyBookShopApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @GetMapping("/search")
    public String search() {
        return "search/index";
    }

    @GetMapping("/documents")
    public String docs() {
        return "documents/index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
}
