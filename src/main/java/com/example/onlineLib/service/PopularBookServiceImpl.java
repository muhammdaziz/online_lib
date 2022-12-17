package com.example.onlineLib.service;

import com.example.onlineLib.entity.Book;
import com.example.onlineLib.entity.PopularBook;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.PopularBookAddDTO;
import com.example.onlineLib.payload.PopularBookDTO;
import com.example.onlineLib.repository.BookRepository;
import com.example.onlineLib.repository.PopularBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopularBookServiceImpl implements PopularBookService {
    private final PopularBookRepository popularBookRepository;

    private final BookRepository bookRepository;

    private final BookServiceImpl bookService;

    @Override
    public ApiResult<Boolean> add(PopularBookAddDTO popularBookAddDTO) {

        if(popularBookRepository.existsByBookId(popularBookAddDTO.getBookId()))
            throw RestException
                    .restThrow("ALREADY EXIST", HttpStatus.CONFLICT);

        Book book = bookRepository.findById(popularBookAddDTO.getBookId())
                .orElseThrow(() ->
                        RestException
                                .restThrow("BOOK NOT FOUND", HttpStatus.NOT_FOUND));


        PopularBook popularBook = new PopularBook();
        popularBook.setText(popularBookAddDTO.getText());
        popularBook.setOption(popularBookAddDTO.getOption());
        popularBook.setBook(book);

        popularBookRepository.save(popularBook);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<PopularBookDTO> get(UUID id) {

        PopularBook popularBook = popularBookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("PopularBook not found", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapPopularBookDTO(popularBook));
    }

    @Override
    public ApiResult<PopularBookDTO> popular() {
        PopularBook popularBook = popularBookRepository.findMostPopular()
                .orElseThrow(() ->
                        RestException.restThrow("POPULAR BOOK NOT EXIST YET", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapPopularBookDTO(popularBook));
    }

    @Override
    public ApiResult<List<PopularBookDTO>> list() {

        List<PopularBook> popularBooks = popularBookRepository.findAllByDeletedFalse();

        return ApiResult.successResponse(mapPopularBookDTO(popularBooks));
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {

        popularBookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("PopularBook not found", HttpStatus.NOT_FOUND));

        popularBookRepository.deleteById(id);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<Boolean> update(PopularBookDTO popularBookDTO) {
        return ApiResult.successResponse();
    }

    private List<PopularBookDTO> mapPopularBookDTO(List<PopularBook> popularBooks){
        return popularBooks.stream()
                .map(this::mapPopularBookDTO)
                .collect(Collectors.toList());
    }

    private PopularBookDTO mapPopularBookDTO(PopularBook popularBook) {

        return PopularBookDTO.builder()
                .id(popularBook.getId())
                .text(popularBook.getText())
                .option(popularBook.getOption())
                .book(bookService.mapBookDTO(popularBook.getBook()))
                .build();
    }
}
