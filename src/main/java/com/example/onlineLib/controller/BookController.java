package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.BookAddDTO;
import com.example.onlineLib.payload.BookDTO;
import com.example.onlineLib.utils.RestConstants;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("api/book")
public interface BookController {

    @PostMapping(value = "/new")
    ApiResult<Boolean> add(@RequestBody BookAddDTO bookAddDTO) throws IOException;

    @GetMapping("/{id}")
    ApiResult<BookDTO> book(@PathVariable Long id);

    @GetMapping("/list")
    ApiResult<List<BookDTO>> books();

    @PutMapping(value="/{id}")
    ApiResult<Boolean> edit(@PathVariable Long id);

    @DeleteMapping(value="/{id}")
    ApiResult<Boolean> delete(@PathVariable Long id);


    @GetMapping("/download/{id}")
    void download(@PathVariable Long id, HttpServletResponse response) throws IOException;
}
