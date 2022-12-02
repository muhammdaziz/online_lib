package com.example.onlineLib.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.core.io.InputStreamResource;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    Long id;

    String title;

    String author;

    String language;

    String context;

    Float price;

    InputStreamResource img;

    String path;
}
