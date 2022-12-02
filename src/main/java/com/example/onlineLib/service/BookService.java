package com.example.onlineLib.service;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.BookAddDTO;
import com.example.onlineLib.payload.BookDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BookService {

    ApiResult<Boolean> add(BookAddDTO bookAddDTO) throws IOException;

    ApiResult<BookDTO> get(Long id);

    ApiResult<List<BookDTO>> list();

    ApiResult<Boolean> delete(Long id);

    ApiResult<Boolean> update(Long id);

    void download(Long id, HttpServletResponse response) throws IOException;
}
