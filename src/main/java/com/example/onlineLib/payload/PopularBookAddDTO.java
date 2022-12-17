package com.example.onlineLib.payload;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PopularBookAddDTO {

    private String option;

    private String text;

    private UUID bookId;
}
