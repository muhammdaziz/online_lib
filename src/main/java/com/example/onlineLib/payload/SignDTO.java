package com.example.onlineLib.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@ToString
public class SignDTO {

    @NotBlank(message = "{MUST_NOT_BE_BLANK_EMAIL}")
    @Email
    private String email;

    @NotBlank(message = "{MUST_NOT_BE_BLANK_PASSWORD}")
    private String password;


    @Override
    public boolean equals(Object obj) {
        SignDTO other = (SignDTO) obj;

        if (!other.getEmail().equals(getEmail()))
            return false;

        return other.getPassword().equals(getPassword());
    }
}

