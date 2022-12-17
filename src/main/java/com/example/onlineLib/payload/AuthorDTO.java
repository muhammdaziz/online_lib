package com.example.onlineLib.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class AuthorDTO {

    private UUID id;

    private String firstname;

    private String lastname;

    private String about;

    private String image;
}
