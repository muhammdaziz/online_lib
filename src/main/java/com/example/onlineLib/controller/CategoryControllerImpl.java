package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.CategoryAddDTO;
import com.example.onlineLib.payload.CategoryDTO;
import com.example.onlineLib.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController{

    private final CategoryService categoryService;

    @Override
    public ApiResult<Boolean> add(CategoryAddDTO categoryAddDTO) {
        return categoryService.add(categoryAddDTO);
    }

    @Override
    public ApiResult<CategoryDTO> category(Integer id) {
        return categoryService.get(id);
    }

    @Override
    public ApiResult<List<CategoryDTO>> categories() {
        return categoryService.list();
    }

    @Override
    public ApiResult<Boolean> edit(CategoryDTO categoryDTO) {
        return categoryService.update(categoryDTO);
    }

    @Override
    public ApiResult<Boolean> delete(Integer id) {
        return categoryService.delete(id);
    }
}
