package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book, Integer> bookRepo;
    private final ProjectRepository<Book, String> bookRepository;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(ProjectRepository<Book, Integer> bookRepo, ProjectRepository<Book, String> bookRepository) {
        this.bookRepo = bookRepo;
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItem("id", bookIdToRemove);
    }

    public boolean removeBookByAuthor(String author) {
        return bookRepository.removeItem("author", author);
    }

    public boolean removeBookByTitle(String title) {
        return bookRepository.removeItem("title", title);
    }

    public boolean removeBookBySize(Integer size) {
        return bookRepo.removeItem("size", size);
    }

    public void defaultInit() {
        logger.info("default INIT in book service");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY in book service");
    }
}
