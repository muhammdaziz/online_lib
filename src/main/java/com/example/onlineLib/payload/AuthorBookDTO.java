package com.example.onlineLib.payload;

import com.example.onlineLib.entity.Author;
import com.example.onlineLib.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class AuthorBookDTO {

    private UUID id;

    private BookDTO book;

    private String text;

    private UUID bgImg;

    private String fontFamily;

    private String fontSize;

}
