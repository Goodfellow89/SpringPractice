package com.example.BookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AuthorList> getAuthors() {

        List<AuthorList> authorLists = new ArrayList<>();

        List<String> letters = getLetters();
        for (String letter : letters) {

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("letter", letter);

            List<String> authors = jdbcTemplate.query("select distinct author from author where letter = :letter order by author", parameterSource, (ResultSet rs, int rowNum) -> rs.getString("author"));

            AuthorList authorList = new AuthorList();
            authorList.setLetter(letter);
            authorList.setAuthor(authors);

            authorLists.add(authorList);
        }
        return authorLists;
    }

    public List<String> getLetters() {
        List<String> letters = jdbcTemplate.query("select distinct letter from author order by letter", (ResultSet rs, int rowNum) -> rs.getString("letter"));
        return new ArrayList<>(letters);
    }
}
