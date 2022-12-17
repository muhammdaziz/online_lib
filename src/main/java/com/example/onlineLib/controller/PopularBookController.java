package com.example.onlineLib.controller;

import com.example.onlineLib.payload.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/book/popular")
public interface PopularBookController {

    @PostMapping(value = "/new")
    @PreAuthorize(value = "hasAnyAuthority('ADD_POPULAR_BOOK')")
    ApiResult<Boolean> add(@RequestBody PopularBookAddDTO popularBookAddDTO);

    @GetMapping("/{id}")
    ApiResult<PopularBookDTO> popularBook(@PathVariable UUID id);

    @GetMapping("")
    ApiResult<PopularBookDTO> popular();

    @GetMapping("/list")
    ApiResult<List<PopularBookDTO>> popularBooks();

    @PutMapping()
    @PreAuthorize(value = "hasAnyAuthority('EDIT_POPULAR_BOOK')")
    ApiResult<Boolean> edit(@RequestBody PopularBookDTO popularBookDTO);

    @DeleteMapping(value="/delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('DELETE_POPULAR_BOOK')")
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
