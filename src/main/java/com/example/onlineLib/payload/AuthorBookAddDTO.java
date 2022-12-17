package com.example.onlineLib.payload;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class AuthorBookAddDTO {

    private UUID bookId;

    private String text;

    private MultipartFile bgImg;

    private String fontFamily;

    private String fontSize;
}
