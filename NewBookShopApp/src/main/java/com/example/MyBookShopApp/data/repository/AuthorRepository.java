package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.dto.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {
}
