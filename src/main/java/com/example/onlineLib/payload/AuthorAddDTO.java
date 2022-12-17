package com.example.onlineLib.payload;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AuthorAddDTO {

    private String firstname;

    private String lastname;

    private String about;

    private MultipartFile image;
}
