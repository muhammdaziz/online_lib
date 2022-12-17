package com.example.onlineLib.service;

import com.example.onlineLib.entity.Author;
import com.example.onlineLib.entity.Book;
import com.example.onlineLib.entity.FileImg;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.*;
import com.example.onlineLib.repository.AuthorRepository;
import com.example.onlineLib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorServiceImpl authorService;

    private final AuthorRepository authorRepository;

    private final IOService ioService;

    @Override
    public ApiResult<Boolean> add(BookAddDTO bookAddDTO) throws IOException {

        Author author = authorRepository.findById(bookAddDTO.getAuthorId())
                .orElseThrow(() ->
                        RestException
                                .restThrow("AUTHOR NOT FOUND", HttpStatus.NOT_FOUND));

        if(bookRepository.existsByTitleAndAuthorId(bookAddDTO.getTitle(), bookAddDTO.getAuthorId()))
            throw RestException
                    .restThrow("BOOK WITH THIS AUTHOR IS EXIST", HttpStatus.CONFLICT);


        Book book = new Book();
        book.setTitle(bookAddDTO.getTitle());
        book.setContext(bookAddDTO.getContext());
        book.setAuthor(author);
        book.setLanguage(bookAddDTO.getLanguage());
        book.setPrice(bookAddDTO.getPrice());


        book.setImage(
                ioService.upload(
                        bookAddDTO.getImage(), true));
        book.setDocument(
                ioService.upload(
                        bookAddDTO.getFile(), false));

        bookRepository.save(book);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<BookDTO> get(UUID id) {

        Book book = bookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("Book not found", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapBookDTO(book));
    }

    @Override
    public ApiResult<BookListDTO> list(Integer pageNumber, Integer size, Integer categoryId) {

        List<Book> books;
        Double booksCount;

        if(categoryId < 0) {
            books = bookRepository.findAllByDeletedFalse(PageRequest.of(pageNumber, size));
            booksCount = bookRepository.findBooksCount();
        }else {
            books = bookRepository.findAllByDeletedFalseAndCategoryId(PageRequest.of(pageNumber, size), categoryId);
            booksCount = bookRepository.findBooksCountByCategory(categoryId);
        }

        booksCount = Math.ceil(booksCount/size);

        System.out.println("pages " + booksCount);

        BookListDTO bookListDTO = BookListDTO.builder()
                .list(mapBookDTO(books))
                .pages(booksCount.intValue())
                .build();

        return ApiResult.successResponse(bookListDTO);
    }

    @Override
    public ApiResult<List<BookDTO>> newBooks() {
        List<Book> books = bookRepository.findAllNewBooks();

        return ApiResult.successResponse(mapBookDTO(books));
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {

        bookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("Book not found", HttpStatus.NOT_FOUND));

        bookRepository.deleteById(id);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<Boolean> update(BookDTO bookDTO) {
        return ApiResult.successResponse();
    }

    public BookDTO mapBookDTO(Book book) {

        Author author = book.getAuthor();

        AuthorDTO authorDTO = null;

        if (author != null)
            authorDTO = authorService.mapAuthorDTO(book.getAuthor());

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .context(book.getContext())
                .language(book.getLanguage())
                .price(book.getPrice())
                .author(authorDTO)
                .document(book.getDocument().getId())
                .image(book.getImage().getId())
                .build();
    }

    private List<BookDTO> mapBookDTO(List<Book> books){
        return books.stream()
                .map(this::mapBookDTO)
                .collect(Collectors.toList());
    }

}
