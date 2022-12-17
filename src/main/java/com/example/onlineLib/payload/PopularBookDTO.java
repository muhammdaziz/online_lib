package com.example.onlineLib.payload;

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
public class PopularBookDTO {

    private UUID id;

    private String option;

    private String text;

    private BookDTO book;
}
