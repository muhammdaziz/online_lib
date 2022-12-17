package com.example.onlineLib.payload;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class BookAddDTO {

    private String title;

    private UUID authorId;

    private String language;

    private String context;

    private Float price;

    private MultipartFile file;

    private MultipartFile image;
}
