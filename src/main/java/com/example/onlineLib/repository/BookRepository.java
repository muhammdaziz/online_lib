package com.example.onlineLib.repository;

import com.example.onlineLib.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query(value = "SELECT price FROM book where id =?", nativeQuery = true)
    Float findPriceById(Long id);

    @Query(value = "SELECT path FROM book where id =?", nativeQuery = true)
    String findPathById(UUID id);
    @Query(value = "SELECT img FROM book where id =?", nativeQuery = true)
    String findImgPathById(UUID id);

    Optional<Book> findByIdAndDeletedFalse(UUID id);

    List<Book> findAllByDeletedFalse(Pageable pageable);

    List<Book> findAllByDeletedFalseAndCategoryId(Pageable pageable, Integer categoryId);

    @Query(value = "SELECT * from book WHERE deleted = false ORDER BY created_at DESC LIMIT 5",nativeQuery = true)
    List<Book> findAllNewBooks();

    @Query(value = "SELECT (SELECT count(*) FROM book WHERE deleted = false)", nativeQuery = true)
    Double findBooksCount();

    @Query(value = "SELECT (SELECT count(*) FROM book WHERE deleted = false AND category_id = :categoryId)", nativeQuery = true)
    Double findBooksCountByCategory(Integer categoryId);

    boolean existsByTitleAndAuthorId(String title, UUID authorId);
}
