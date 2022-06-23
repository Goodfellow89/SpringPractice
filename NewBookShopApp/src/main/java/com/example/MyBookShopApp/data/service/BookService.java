package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.dto.book.BookEntity;
import com.example.MyBookShopApp.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> getBooksData() {
        List<BookEntity> books = bookRepository.findAll();
        return bookRepository.findAll();
    }

//    public List<BookEntity> getBooksInCart() {
//        List<BookEntity> booksInCart = jdbcTemplate.query("select * from books, book2user_type where books.id = book2user_type.id and book2user_type.code = 'CART'", this::fillBook);
//        return new ArrayList<>(booksInCart);
//    }

//    public Double totalCost() {
//        return getBooksInCart().stream().mapToDouble(b -> Double.parseDouble(b.getPrice().substring(1))).sum();
//    }
//
//    public Double totalOldCost() {
//        return getBooksInCart().stream().mapToDouble(b -> Math.max(Double.parseDouble(b.getPrice().substring(1)), Double.parseDouble(b.getPriceOld().substring(1)))).sum();
//    }

//    private BookEntity fillBook(ResultSet rs, int rowNum) {
//        BookEntity book = new BookEntity();
//
//        try {
//            book.setId(rs.getInt("id"));
//            book.setAuthor(rs.getString("author"));
//            book.setTitle(rs.getString("title"));
//            book.setPriceOld(rs.getString("priceOld"));
//            book.setPrice(rs.getString("price"));
//        } catch (
//                SQLException e) {
//            e.printStackTrace();
//        }
//
//        double price = Double.parseDouble(book.getPrice().substring(1));
//        double priceOld = Double.parseDouble(book.getPriceOld().substring(1));
//
//        if ((priceOld - price) > 0) {
//            book.setDisc((int) ((priceOld - price) * 100 / priceOld));
//        } else {
//            book.setDisc(0);
//        }
//
//        return book;
//    }
}
