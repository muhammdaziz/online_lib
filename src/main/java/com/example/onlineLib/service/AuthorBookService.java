package com.example.onlineLib.service;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.AuthorBookAddDTO;
import com.example.onlineLib.payload.AuthorBookDTO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AuthorBookService {

    ApiResult<Boolean> add(AuthorBookAddDTO authorBookAddDTO) throws IOException;

    ApiResult<AuthorBookDTO> get(UUID id);

    ApiResult<List<AuthorBookDTO>> list();

    ApiResult<Boolean> delete(UUID id);

    ApiResult<Boolean> update(AuthorBookDTO authorBookDTO);

    ApiResult<AuthorBookDTO> newBook();
}
