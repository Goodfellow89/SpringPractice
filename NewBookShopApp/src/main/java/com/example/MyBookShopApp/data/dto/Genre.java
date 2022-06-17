package com.example.MyBookShopApp.data.dto;

import lombok.Data;

@Data
public class Genre {
    private Integer id;
    private Integer parentId;
    private String genre;
}
