package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        if (!book.getAuthor().isEmpty() || !book.getTitle().isEmpty() || book.getSize() != null) {
            book.setId(book.hashCode());
            logger.info("store new book: " + book);
            repo.add(book);
        }
        logger.info("No information");
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByAuthor(String author) {
        boolean isPresent = false;
        for (Book book : retrieveAll()) {
            if (book.getAuthor().toUpperCase().equals(author.toUpperCase().trim())) {
                isPresent = true;
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return isPresent;
    }

    @Override
    public boolean removeItemByTitle(String title) {
        boolean isPresent = false;
        for (Book book : retrieveAll()) {
            if (book.getTitle().toUpperCase().equals(title.toUpperCase().trim())) {
                isPresent = true;
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return isPresent;
    }

    @Override
    public boolean removeItemBySize(Integer size) {
        boolean isPresent = false;
        for (Book book : retrieveAll()) {
            if (book.getSize().equals(size)) {
                isPresent = true;
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return isPresent;
    }
}
