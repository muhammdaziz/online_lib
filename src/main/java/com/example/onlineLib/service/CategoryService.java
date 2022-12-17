package com.example.onlineLib.service;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.CategoryAddDTO;
import com.example.onlineLib.payload.CategoryDTO;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    ApiResult<Boolean> add(CategoryAddDTO categoryAddDTO);

    ApiResult<CategoryDTO> get(Integer id);

    ApiResult<List<CategoryDTO>> list();

    ApiResult<Boolean> delete(Integer id);

    ApiResult<Boolean> update(CategoryDTO categoryDTO);
}
