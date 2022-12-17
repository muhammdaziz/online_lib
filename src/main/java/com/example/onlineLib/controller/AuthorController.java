package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.AuthorAddDTO;
import com.example.onlineLib.payload.AuthorDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/author")
public interface AuthorController {

    @PostMapping(value = "/new")
    @PreAuthorize(value = "hasAnyAuthority('ADD_AUTHOR')")
    ApiResult<Boolean> add(@RequestBody AuthorAddDTO authorAddDTO) throws IOException;

    @GetMapping("/{id}")
    ApiResult<AuthorDTO> author(@PathVariable UUID id);

    @GetMapping("/list")
    ApiResult<List<AuthorDTO>> authors();

    @PutMapping()
    @PreAuthorize(value = "hasAnyAuthority('EDIT_AUTHOR')")
    ApiResult<Boolean> edit(@RequestBody AuthorDTO authorDTO);

    @DeleteMapping(value="/delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('DELETE_AUTHOR')")
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
