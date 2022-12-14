package com.example.onlineLib.payload;

import com.example.onlineLib.entity.Author;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class BookDTO {

    private UUID id;

    private String title;

    private AuthorDTO author;

    private String language;

    private String context;

    private Float price;

    private UUID image;

    private UUID document;
}
