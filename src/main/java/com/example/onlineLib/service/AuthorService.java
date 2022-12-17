package com.example.onlineLib.service;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.AuthorAddDTO;
import com.example.onlineLib.payload.AuthorDTO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AuthorService {

    ApiResult<Boolean> add(AuthorAddDTO bookAddDTO) throws IOException;

    ApiResult<AuthorDTO> get(UUID id);

    ApiResult<List<AuthorDTO>> list();

    ApiResult<Boolean> delete(UUID id);

    ApiResult<Boolean> update(AuthorDTO bookDTO);

}
