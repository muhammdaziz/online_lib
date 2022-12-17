package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.CategoryAddDTO;
import com.example.onlineLib.payload.CategoryDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("api/category")
public interface CategoryController {

    @PostMapping(value = "/new")
    @PreAuthorize(value = "hasAnyAuthority('ADD_CATEGORY')")
    ApiResult<Boolean> add(@RequestBody CategoryAddDTO categoryAddDTO);

    @GetMapping("/{id}")
    ApiResult<CategoryDTO> category(@PathVariable Integer id);

    @GetMapping("/list")
    ApiResult<List<CategoryDTO>> categories();

    @PutMapping()
    @PreAuthorize(value = "hasAnyAuthority('EDIT_CATEGORY')")
    ApiResult<Boolean> edit(@RequestBody CategoryDTO categoryDTO);

    @DeleteMapping(value="/delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('DELETE_CATEGORY')")
    ApiResult<Boolean> delete(@PathVariable Integer id);

}
