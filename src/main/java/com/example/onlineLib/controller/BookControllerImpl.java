package com.example.onlineLib.controller;

import com.example.onlineLib.payload.*;
import com.example.onlineLib.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController{

    private final BookService bookService;

    @Override
    public ApiResult<Boolean> add(BookAddDTO bookAddDTO) throws IOException {
        return bookService.add(bookAddDTO);
    }

    @Override
    public ApiResult<BookDTO> book(UUID id) {
        return bookService.get(id);
    }

    @Override
    public ApiResult<BookListDTO> books(Integer pageNumber, Integer size, Integer categoryId) {
        return bookService.list(pageNumber, size, categoryId);
    }

    @Override
    public ApiResult<List<BookDTO>> newBooks() {
        return bookService.newBooks();
    }

    @Override
    public ApiResult<Boolean> edit(BookDTO bookDTO) {
        return bookService.update(bookDTO);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        return bookService.delete(id);
    }
}
