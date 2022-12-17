package com.example.onlineLib.service;

import com.example.onlineLib.entity.Author;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.AuthorAddDTO;
import com.example.onlineLib.payload.AuthorDTO;
import com.example.onlineLib.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final IOService ioService;

    @Override
    public ApiResult<Boolean> add(AuthorAddDTO authorAddDTO) throws IOException {

        Author author = new Author();
        author.setFirstname(authorAddDTO.getFirstname());
        author.setLastname(authorAddDTO.getLastname());
        author.setAbout(authorAddDTO.getAbout());
        author.setImage(
                ioService.upload(
                        authorAddDTO.getImage(), true));

        authorRepository.save(author);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<AuthorDTO> get(UUID id) {

        Author author = authorRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("Author not found", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapAuthorDTO(author));
    }

    @Override
    public ApiResult<List<AuthorDTO>> list() {

        List<Author> authors = authorRepository.findAllByDeletedFalse();

        return ApiResult.successResponse(mapAuthorDTO(authors));
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {

        Author author = authorRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("Author not found", HttpStatus.NOT_FOUND));

        authorRepository.deleteById(id);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<Boolean> update(AuthorDTO authorDTO) {
        return ApiResult.successResponse();
    }

    public AuthorDTO mapAuthorDTO(Author author) {

        return AuthorDTO.builder()
                .id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .about(author.getAbout())
                .image(""+author.getImage().getId())
                .build();
    }

    private List<AuthorDTO> mapAuthorDTO(List<Author> authors){
        return authors.stream()
                .map(this::mapAuthorDTO)
                .collect(Collectors.toList());
    }

}
