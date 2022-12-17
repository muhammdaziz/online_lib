package com.example.onlineLib.service;

import com.example.onlineLib.entity.FileImg;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.repository.BookRepository;
import com.example.onlineLib.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class IOServiceImpl implements IOService{

    private final Path path;

    private final BookRepository bookRepository;

    private final FileRepository fileRepository;

    private static final int BUFFER_SIZE = 4096;

    private static final String UPLOAD_DIRECTORY = "/Users/user/Desktop/web/";

    public IOServiceImpl(BookRepository bookRepository, FileRepository fileRepository) {
        this.bookRepository = bookRepository;
        this.fileRepository = fileRepository;
        this.path = Paths
                .get(UPLOAD_DIRECTORY)
                .toAbsolutePath().normalize();

        createDirectories(path, "img");
        createDirectories(path, "pdf");
    }

    @Override
    public FileImg upload(MultipartFile originalFile, boolean isImg) throws IOException {

        if (originalFile.isEmpty())
            throw RestException.restThrow("No file chosen", HttpStatus.BAD_REQUEST);

        String url = isImg ? UPLOAD_DIRECTORY + "img" : "pdf";

        Path path = Paths.get(url, originalFile.getOriginalFilename());

        Files.write(path, originalFile.getBytes());

        FileImg file = new FileImg();
        file.setName(originalFile.getOriginalFilename());
        file.setPath(path.toString());

        return fileRepository.save(file);
    }

    @Override
    public void download(UUID id, HttpServletResponse response) throws IOException {
        String filePath = getBookPath(id);

        File downloadFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());

        response.setHeader(headerKey, headerValue);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    @Override
    public void serveImage(UUID id, HttpServletResponse response) throws IOException {
        System.out.println("id " + id);

        String filePath = bookRepository.findImgPathById(id);

        System.out.println("p " + filePath);

        InputStream resource = new FileInputStream(filePath);

        response.setContentType(MediaType.ALL_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }

    private void createDirectories(Path path, String folder) {
        try {
            path = Paths
                    .get(path + folder)
                    .toAbsolutePath().normalize();

            Files.createDirectories(path);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private String getBookPath(UUID id) {
        return bookRepository.findPathById(id);
    }
}
