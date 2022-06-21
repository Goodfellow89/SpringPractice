package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.dto.book.BookEntity;
import com.example.MyBookShopApp.data.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class ProductController {

    private final BookService bookService;

    @Autowired
    public ProductController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("postponed")
    public List<BookEntity> postponed() {
        return bookService.getBooksData();
    }

    @GetMapping("/postponed")
    public String postponedPage() {
        return "postponed";
    }

//    @ModelAttribute("addedToCart")
////    public List<BookEntity> addedToCart() {
////        return bookService.getBooksInCart();
////    }
////    @ModelAttribute("totalCost")
////    public Double totalCost() {
////        return bookService.totalCost();
////    }
////    @ModelAttribute("totalOldCost")
////    public Double totalOldCost() {
////        return bookService.totalOldCost();
////    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }
}
