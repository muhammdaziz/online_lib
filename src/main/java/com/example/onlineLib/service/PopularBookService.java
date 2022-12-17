package com.example.onlineLib.service;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.PopularBookAddDTO;
import com.example.onlineLib.payload.PopularBookDTO;

import java.util.List;
import java.util.UUID;

public interface PopularBookService {

    ApiResult<Boolean> add(PopularBookAddDTO bookAddDTO);

    ApiResult<PopularBookDTO> get(UUID id);

    ApiResult<PopularBookDTO> popular();

    ApiResult<List<PopularBookDTO>> list();

    ApiResult<Boolean> delete(UUID id);

    ApiResult<Boolean> update(PopularBookDTO bookDTO);

}
