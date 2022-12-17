package com.example.onlineLib.service;

import com.example.onlineLib.entity.Category;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.CategoryAddDTO;
import com.example.onlineLib.payload.CategoryDTO;
import com.example.onlineLib.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResult<Boolean> add(CategoryAddDTO categoryAddDTO) {

        if(categoryRepository.existsByName(categoryAddDTO.getName()))
            throw RestException
                    .restThrow("CATEGORY ALREADY EXIST", HttpStatus.CONFLICT);

        Category parentCategory = null;

        if (!Objects.isNull(categoryAddDTO.getParentId()))
            parentCategory = categoryRepository
                    .findById(categoryAddDTO.getParentId())
                    .orElseThrow(() ->
                        RestException
                            .restThrow("PARENT CATEGORY NOT FOUND", HttpStatus.NOT_FOUND));

        Category category = new Category();
        category.setName(category.getName());
        category.setCategory(parentCategory);

        categoryRepository.save(category);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<CategoryDTO> get(Integer id) {

        Category category = categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(
                    () -> RestException
                            .restThrow("CATEGORY NOT FOUND", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapCategoryDTO(category));
    }

    @Override
    public ApiResult<List<CategoryDTO>> list() {

        List<Category> categories = categoryRepository.findAllByDeletedFalse();

        return ApiResult.successResponse(mapCategoryDTO(categories));
    }

    @Override
    public ApiResult<Boolean> delete(Integer id) {

        categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("CATEGORY NOT FOUND", HttpStatus.NOT_FOUND));

        categoryRepository.deleteById(id);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<Boolean> update(CategoryDTO categoryDTO) {
        return ApiResult.successResponse();
    }

    private List<CategoryDTO> mapCategoryDTO(List<Category> categories){
        return categories.stream()
                .map(this::mapCategoryDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO mapCategoryDTO(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(Objects.nonNull(category.getCategory()) ? category.getCategory().getId() : null)
                .build();
    }
}
