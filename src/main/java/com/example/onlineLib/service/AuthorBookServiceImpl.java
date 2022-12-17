package com.example.onlineLib.service;

import com.example.onlineLib.entity.Author;
import com.example.onlineLib.entity.AuthorBook;
import com.example.onlineLib.entity.Book;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.*;
import com.example.onlineLib.repository.AuthorRepository;
import com.example.onlineLib.repository.AuthorBookRepository;
import com.example.onlineLib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorBookServiceImpl implements AuthorBookService {
    private final AuthorBookRepository authorBookRepository;

    private final AuthorRepository authorRepository;

    private final IOService ioService;

    private final BookRepository bookRepository;

    private final BookServiceImpl bookService;

    private final AuthorServiceImpl authorService;

    @Override
    public ApiResult<Boolean> add(AuthorBookAddDTO authorBookAddDTO) throws IOException {


        Book book = bookRepository.findById(authorBookAddDTO.getBookId())
                .orElseThrow(() ->
                        RestException
                                .restThrow("BOOK NOT FOUND", HttpStatus.NOT_FOUND));

        authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() ->
                        RestException
                                .restThrow("AUTHOR NOT FOUND", HttpStatus.NOT_FOUND));

        if(authorBookRepository.existsByBookId(book.getId()))
            throw RestException
                    .restThrow("BOOK WITH THIS AUTHOR IS EXIST", HttpStatus.CONFLICT);


        AuthorBook authorBook = new AuthorBook();
        authorBook.setBook(book);
        authorBook.setText(authorBookAddDTO.getText());
        authorBook.setFontSize(authorBookAddDTO.getFontSize());
        authorBook.setFontFamily(authorBookAddDTO.getFontFamily());
        authorBook.setBgImg(
                ioService.upload(
                        authorBookAddDTO.getBgImg(), true));

        authorBookRepository.save(authorBook);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<AuthorBookDTO> get(UUID id) {

        AuthorBook authorBook = authorBookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("AUTHOR_BOOK NOT FOUND", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapAuthorBookDTO(authorBook));
    }

    @Override
    public ApiResult<List<AuthorBookDTO>> list() {

        List<AuthorBook> authorBooks = authorBookRepository.findAllByDeletedFalse();

        return ApiResult.successResponse(mapAuthorBookDTO(authorBooks));
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {

        authorBookRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(
                    () -> RestException
                        .restThrow("AUTHOR_BOOK NOT FOUND", HttpStatus.NOT_FOUND));

        authorBookRepository.deleteById(id);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<Boolean> update(AuthorBookDTO authorBookDTO) {
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<AuthorBookDTO> newBook() {

        AuthorBook authorBook = authorBookRepository.findLast()
                .orElseThrow(() ->
                        RestException
                                .restThrow("AUTHOR_BOOK_NOT FOUND", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapAuthorBookDTO(authorBook));
    }

    public AuthorBookDTO mapAuthorBookDTO(AuthorBook authorBook) {

        Book book = authorBook.getBook();

        BookDTO bookDTO = null;

        if (book != null){
            bookDTO = bookService.mapBookDTO(book);

            Author author = book.getAuthor();

            AuthorDTO authorDTO = null;

            if (author != null)
                authorDTO = authorService.mapAuthorDTO(author);

            bookDTO.setImage(book.getImage().getId());
            bookDTO.setDocument(book.getDocument().getId());
            bookDTO.setAuthor(authorDTO);

        }


        return AuthorBookDTO.builder()
                .id(authorBook.getId())
                .text(authorBook.getText())
                .bgImg(authorBook.getId())
                .fontFamily(authorBook.getFontFamily())
                .fontSize(authorBook.getFontSize())
                .book(bookDTO)
                .build();
    }

    private List<AuthorBookDTO> mapAuthorBookDTO(List<AuthorBook> authorBooks){
        return authorBooks.stream()
                .map(this::mapAuthorBookDTO)
                .collect(Collectors.toList());
    }

}
