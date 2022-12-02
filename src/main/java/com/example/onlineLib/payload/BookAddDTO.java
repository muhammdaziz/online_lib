package com.example.onlineLib.payload;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BookAddDTO {

    String title;

    String author;

    String language;

    String context;

    Float price;

    MultipartFile file;

    MultipartFile image;
}
