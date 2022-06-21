package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.dto.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

}
