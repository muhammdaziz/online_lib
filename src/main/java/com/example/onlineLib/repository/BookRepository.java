package com.example.onlineLib.repository;

import com.example.onlineLib.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT price FROM book where id =?", nativeQuery = true)
    Float findPriceById(Long id);

    @Query(value = "SELECT path FROM book where id =?", nativeQuery = true)
    String findPathById(Long id);

    Optional<Book> findByIdAndDeletedFalse(Long id);

    List<Book> findAllByDeletedFalse();
}
