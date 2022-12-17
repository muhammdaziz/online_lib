package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.AuthorBookAddDTO;
import com.example.onlineLib.payload.AuthorBookDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/book/author-book")
public interface AuthorBookController {

    @PostMapping(value = "/new")
    @PreAuthorize(value = "hasAnyAuthority('ADD_AUTHOR_BOOK')")
    ApiResult<Boolean> add(@RequestBody AuthorBookAddDTO authorBookAddDTO) throws IOException;

    @GetMapping("/{id}")
    ApiResult<AuthorBookDTO> authorBook(@PathVariable UUID id);

    @GetMapping("/new-book")
    ApiResult<AuthorBookDTO> newBook();

    @GetMapping("/list")
    ApiResult<List<AuthorBookDTO>> authorBooks();

    @PutMapping()
    @PreAuthorize(value = "hasAnyAuthority('EDIT_AUTHOR_BOOK')")
    ApiResult<Boolean> edit(@RequestBody AuthorBookDTO authorBookDTO);

    @DeleteMapping(value="/delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('DELETE_AUTHOR_BOOK')")
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
