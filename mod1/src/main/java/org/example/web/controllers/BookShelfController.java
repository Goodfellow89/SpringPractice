package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") String bookIdToRemove) {
        Integer id;

        try {
            id = Integer.parseInt(bookIdToRemove);
        } catch (NumberFormatException e) {
            logger.info("Wrong format");
            return "redirect:/books/shelf";
        }
        if (bookService.removeBookById(id)) {
            logger.info("book " + id + " has been removed");
            return "redirect:/books/shelf";
        }
        logger.info("wrong book_id");
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeByAuthor")
    public String removeByAuthor (@RequestParam("queryRegex") String author) {
        if (bookService.removeBookByAuthor(author)) {
            logger.info("All books of " + author + " have been removed");
            return "redirect:/books/shelf";
        }
        logger.info("There's no books of " + author);
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeByTitle")
    public String removeByTitle (@RequestParam("queryRegex") String title) {
        if (bookService.removeBookByTitle(title)) {
            logger.info("All books with title \"" + title + "\" have been removed");
            return "redirect:/books/shelf";
        }
        logger.info("There's no books with title \"" + title + "\"");
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeBySize")
    public String removeBySize (@RequestParam("queryRegex") String size) {
        Integer bookSize;

        try {
             bookSize = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            logger.info("Wrong format");
            return "redirect:/books/shelf";
        }

        if (bookService.removeBookBySize(bookSize)) {
            logger.info("All books with size of " + bookSize + " pages have been removed");
            return "redirect:/books/shelf";
        }
        logger.info("There's no books with size of " + size + " pages");
        return "redirect:/books/shelf";
    }
}
