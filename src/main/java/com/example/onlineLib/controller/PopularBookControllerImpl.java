package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.PopularBookAddDTO;
import com.example.onlineLib.payload.PopularBookDTO;
import com.example.onlineLib.service.PopularBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class PopularBookControllerImpl implements PopularBookController{

    private final PopularBookService popularBookService;

    @Override
    public ApiResult<Boolean> add(PopularBookAddDTO popularBookAddDTO) {
        return popularBookService.add(popularBookAddDTO);
    }

    @Override
    public ApiResult<PopularBookDTO> popularBook(UUID id) {
        return popularBookService.get(id);
    }

    @Override
    public ApiResult<PopularBookDTO> popular() {
        return popularBookService.popular();
    }

    @Override
    public ApiResult<List<PopularBookDTO>> popularBooks() {
        return popularBookService.list();
    }

    @Override
    public ApiResult<Boolean> edit(PopularBookDTO popularBookDTO) {
        return popularBookService.update(popularBookDTO);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        return popularBookService.delete(id);
    }
}
