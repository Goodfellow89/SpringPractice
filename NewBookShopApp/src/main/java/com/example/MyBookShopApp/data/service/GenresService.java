package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.dto.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenresService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GenresService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Map<String, List<Genre>>> getGenres() {

        Map<String, Map<String, List<Genre>>> genresMap = new HashMap<>();
        List<Genre> mainGenres = jdbcTemplate.query("select * from genres where parentId is null", this::fillGenre);

        for (Genre mg : mainGenres) {

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id", mg.getId());

            List<Genre> middleLevel = jdbcTemplate.query("select * from genres where parentId = :id", param, this::fillGenre);

            if (middleLevel.isEmpty()) {
                genresMap.put(mg.getGenre(), null);
            } else {
                Map<String, List<Genre>> genreMap = new HashMap<>();
                for (Genre g : middleLevel) {

                    MapSqlParameterSource secondParam = new MapSqlParameterSource();
                    secondParam.addValue("id", g.getId());

                    List<Genre> lowLevel = jdbcTemplate.query("select * from genres where parentId = :id", secondParam, this::fillGenre);

                    if (lowLevel.isEmpty()) {
                        genreMap.put(g.getGenre(), null);
                    } else {
                        genreMap.put(g.getGenre(), lowLevel);
                    }
                }
                genresMap.put(mg.getGenre(), genreMap);
            }
        }

        return genresMap;
    }

    private Genre fillGenre(ResultSet rs, int rowNum) {
        Genre genre = new Genre();
        try {
            genre.setId(rs.getInt("id"));
            genre.setParentId(rs.getInt("parentId"));
            genre.setGenre(rs.getString("genre"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }
}
