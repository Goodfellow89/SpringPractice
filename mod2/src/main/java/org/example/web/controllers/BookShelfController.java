package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.BookParamToRemove;
import org.example.web.dto.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        addModelAttributes(model, new Book(), new BookIdToRemove(), new BookParamToRemove(), new UploadFile());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (book.getAuthor().equals("") && book.getTitle().equals("") && book.getSize() == null) {
            bindingResult.addError(new ObjectError("book", "Empty fields"));
        }
        if (bindingResult.hasErrors()) {
            addModelAttributes(model, book, new BookIdToRemove(), new BookParamToRemove(), new UploadFile());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addModelAttributes(model, new Book(), bookIdToRemove, new BookParamToRemove(), new UploadFile());
            return "book_shelf";
        } else {
            if (bookService.removeBookById(bookIdToRemove.getId())) {
                return "redirect:/books/shelf";
            } else {
                bindingResult.addError(new ObjectError("bookIdToRemove", "Not found"));
                addModelAttributes(model, new Book(), bookIdToRemove, new BookParamToRemove(), new UploadFile());
                return "book_shelf";
            }
        }
    }

    @PostMapping("/removeByAuthor")
    public String removeByAuthor(@Valid BookParamToRemove paramToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addModelAttributes(model, new Book(), new BookIdToRemove(), paramToRemove, new UploadFile());
            return "book_shelf";
        } else {
            if (bookService.removeBookByAuthor(paramToRemove.getParam())) {
                return "redirect:/books/shelf";
            } else {
                bindingResult.addError(new ObjectError("paramToRemove", "Not found"));
                addModelAttributes(model, new Book(), new BookIdToRemove(), paramToRemove, new UploadFile());
                return "book_shelf";
            }
        }
    }

    @PostMapping("/removeByTitle")
    public String removeByTitle(@Valid BookParamToRemove paramToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addModelAttributes(model, new Book(), new BookIdToRemove(), paramToRemove, new UploadFile());
            return "book_shelf";
        } else {
            if (bookService.removeBookByTitle(paramToRemove.getParam())) {
                return "redirect:/books/shelf";
            } else {
                bindingResult.addError(new ObjectError("paramToRemove", "Not found"));
                addModelAttributes(model, new Book(), new BookIdToRemove(), paramToRemove, new UploadFile());
                return "book_shelf";
            }
        }
    }

    @PostMapping("/removeBySize")
    public String removeBySize(@Valid BookParamToRemove paramToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addModelAttributes(model, new Book(), new BookIdToRemove(), paramToRemove, new UploadFile());
            return "book_shelf";
        } else {
            Integer size;
            try {
                size = Integer.parseInt(paramToRemove.getParam());
            } catch (NumberFormatException e) {
                size = null;
            }
            if (size != null && bookService.removeBookBySize(size)) {
                return "redirect:/books/shelf";
            } else {
                bindingResult.addError(new ObjectError("paramToRemove", "Not found"));
                addModelAttributes(model, new Book(), new BookIdToRemove(), paramToRemove, new UploadFile());
                return "book_shelf";
            }
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(UploadFile uploadFile, BindingResult bindingResult, Model model) throws Exception {
        if (uploadFile.getFile().isEmpty()) {
            bindingResult.addError(new ObjectError("file", "You have to choose file"));
            addModelAttributes(model, new Book(), new BookIdToRemove(), new BookParamToRemove(), uploadFile);
            return "book_shelf";
        } else {
            String name = uploadFile.getFile().getOriginalFilename();
            byte[] bytes = uploadFile.getFile().getBytes();

            //create dir
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "external_uploads");

            //create file
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("new file saved at: " + serverFile.getAbsolutePath());

            return "redirect:/books/shelf";
        }
    }

    private void addModelAttributes(Model model, Book book, BookIdToRemove bookIdToRemove, BookParamToRemove bookParamToRemove, UploadFile uploadFile) {
        model.addAttribute("book", book);
        model.addAttribute("bookIdToRemove", bookIdToRemove);
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("bookParamToRemove", bookParamToRemove);
        model.addAttribute("uploadFile", uploadFile);
    }
}
