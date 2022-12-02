package com.example.onlineLib.service;

import com.example.onlineLib.entity.Book;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.BookAddDTO;
import com.example.onlineLib.payload.BookDTO;
import com.example.onlineLib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final int BUFFER_SIZE = 4096;

    private static final String UPLOAD_DIRECTORY = "/Users/user/Desktop/web";

    private final BookRepository bookRepository;

    @Override
    public ApiResult<Boolean> add(BookAddDTO bookAddDTO) throws IOException {

        MultipartFile file = bookAddDTO.getFile();

        MultipartFile image = bookAddDTO.getImage();

        if (file.isEmpty() || image.isEmpty())
            throw RestException.restThrow("No file chosen", HttpStatus.BAD_REQUEST);

        Book book = new Book();
        book.setTitle(bookAddDTO.getTitle());
        book.setContext(bookAddDTO.getContext());
        book.setAuthor(bookAddDTO.getAuthor());
        book.setLanguage(bookAddDTO.getLanguage());
        book.setPrice(bookAddDTO.getPrice());

        Path path1 = Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename());
        Files.write(path1, image.getBytes());
        book.setImg(path1.toString());

        Path path = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(path, file.getBytes());
        book.setPath(path.toString());

        bookRepository.save(book);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<BookDTO> get(Long id) {

        Book book = bookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("Book not found", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapBookDTO(book));
    }

    @Override
    public ApiResult<List<BookDTO>> list() {

        List<Book> books = bookRepository.findAllByDeletedFalse();

        return ApiResult.successResponse(mapBookDTO(books));
    }

    @Override
    public ApiResult<Boolean> delete(Long id) {

        Book book = bookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> RestException
                        .restThrow("Book not found", HttpStatus.NOT_FOUND));

        bookRepository.deleteById(id);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<Boolean> update(Long id) {
        return null;
    }

    @Override
    public void download(Long id, HttpServletResponse response) throws IOException {
        String filePath = getBookPath(id);

        // construct the complete absolute path of the file
        File downloadFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
//
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    private String getBookPath(Long id) {
        return bookRepository.findPathById(id);
    }

    private List<BookDTO> mapBookDTO(List<Book> books){
        return books.stream()
                .map(this::mapBookDTO)
                .collect(Collectors.toList());
    }

    private BookDTO mapBookDTO(Book book) {

        InputStreamResource img = new InputStreamResource(
                Objects
                        .requireNonNull(
                                getClass().getResourceAsStream(
                                        book.getImg())));

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .context(book.getContext())
                .language(book.getLanguage())
                .price(book.getPrice())
                .author(book.getAuthor())
                .path(book.getPath())
                .img(img)
                .build();
    }
}
