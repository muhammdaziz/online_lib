package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.BookAddDTO;
import com.example.onlineLib.payload.BookDTO;
import com.example.onlineLib.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController{

    private final BookService bookService;

    @Override
    public ApiResult<Boolean> add(BookAddDTO bookAddDTO) throws IOException {
        return bookService.add(bookAddDTO);
    }

    @Override
    public ApiResult<BookDTO> book(Long id) {
        return bookService.get(id);
    }

    @Override
    public ApiResult<List<BookDTO>> books() {
        return bookService.list();
    }

    @Override
    public ApiResult<Boolean> edit(Long id) {
        return bookService.update(id);
    }

    @Override
    public ApiResult<Boolean> delete(Long id) {
        return bookService.delete(id);
    }

    @Override
    public void download(Long id, HttpServletResponse response) throws IOException {
        bookService.download(id, response);
    }
}
