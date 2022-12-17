package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.AuthorAddDTO;
import com.example.onlineLib.payload.AuthorDTO;
import com.example.onlineLib.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController{

    private final AuthorService authorService;

    @Override
    public ApiResult<Boolean> add(AuthorAddDTO authorAddDTO) throws IOException {
        return authorService.add(authorAddDTO);
    }

    @Override
    public ApiResult<AuthorDTO> author(UUID id) {
        return authorService.get(id);
    }

    @Override
    public ApiResult<List<AuthorDTO>> authors() {
        return authorService.list();
    }

    @Override
    public ApiResult<Boolean> edit(AuthorDTO authorDTO) {
        return authorService.update(authorDTO);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        return authorService.delete(id);
    }
}
