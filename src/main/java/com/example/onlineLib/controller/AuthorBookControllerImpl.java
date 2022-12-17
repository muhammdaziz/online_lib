package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.AuthorBookAddDTO;
import com.example.onlineLib.payload.AuthorBookDTO;
import com.example.onlineLib.service.AuthorBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class AuthorBookControllerImpl implements AuthorBookController{

    private final AuthorBookService authorBookService;

    @Override
    public ApiResult<Boolean> add(AuthorBookAddDTO authorBookAddDTO) throws IOException {
        return authorBookService.add(authorBookAddDTO);
    }

    @Override
    public ApiResult<AuthorBookDTO> authorBook(UUID id) {
        return authorBookService.get(id);
    }

    @Override
    public ApiResult<AuthorBookDTO> newBook() {
        return authorBookService.newBook();
    }

    @Override
    public ApiResult<List<AuthorBookDTO>> authorBooks() {
        return authorBookService.list();
    }

    @Override
    public ApiResult<Boolean> edit(AuthorBookDTO authorBookDTO) {
        return authorBookService.update(authorBookDTO);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        return authorBookService.delete(id);
    }
}
