package com.example.onlineLib.service;

import com.example.onlineLib.payload.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface BookService {

    ApiResult<Boolean> add(BookAddDTO bookAddDTO) throws IOException;

    ApiResult<BookDTO> get(UUID id);

    ApiResult<BookListDTO> list(Integer pageNumber, Integer size, Integer categoryId);

    ApiResult<List<BookDTO>> newBooks();

    ApiResult<Boolean> delete(UUID id);

    ApiResult<Boolean> update(BookDTO bookDTO);

}
