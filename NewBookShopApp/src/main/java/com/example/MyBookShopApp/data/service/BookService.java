package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooksData() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", this::fillBook);
        return new ArrayList<>(books);
    }

    public List<Book> getBooksInCart() {
        List<Book> booksInCart = jdbcTemplate.query("select * from books, book2user_type where books.id = book2user_type.id and book2user_type.code = 'CART'", this::fillBook);
        return new ArrayList<>(booksInCart);
    }

    public Double totalCost() {
        return getBooksInCart().stream().mapToDouble(b -> Double.parseDouble(b.getPrice().substring(1))).sum();
    }

    public Double totalOldCost() {
        return getBooksInCart().stream().mapToDouble(b -> Math.max(Double.parseDouble(b.getPrice().substring(1)), Double.parseDouble(b.getPriceOld().substring(1)))).sum();
    }

    private Book fillBook(ResultSet rs, int rowNum) {
        Book book = new Book();

        try {
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getString("priceOld"));
            book.setPrice(rs.getString("price"));
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        double price = Double.parseDouble(book.getPrice().substring(1));
        double priceOld = Double.parseDouble(book.getPriceOld().substring(1));

        if ((priceOld - price) > 0) {
            book.setDisc((int) ((priceOld - price) * 100 / priceOld));
        } else {
            book.setDisc(0);
        }

        return book;
    }
}
