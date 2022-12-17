package com.example.onlineLib.controller;

import com.example.onlineLib.payload.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/book")
public interface BookController {

    @PostMapping(value = "/new")
    @PreAuthorize(value = "hasAnyAuthority('ADD_BOOK')")
    ApiResult<Boolean> add(@RequestBody BookAddDTO bookAddDTO) throws IOException;

    @GetMapping("/{id}")
    ApiResult<BookDTO> book(@PathVariable UUID id);

    @GetMapping("/list/{pageNumber}/{size}/{categoryId}")
    ApiResult<BookListDTO> books(@PathVariable Integer pageNumber, @PathVariable Integer size, @PathVariable Integer categoryId);

    @GetMapping("/list/news")
    ApiResult<List<BookDTO>> newBooks();

    @PutMapping()
    @PreAuthorize(value = "hasAnyAuthority('EDIT_BOOK')")
    ApiResult<Boolean> edit(@RequestBody BookDTO bookDTO);

    @DeleteMapping(value="/delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('DELETE_BOOK')")
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
