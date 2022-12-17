package com.example.onlineLib.repository;

import com.example.onlineLib.entity.AuthorBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBook, UUID> {

    Optional<AuthorBook> findByIdAndDeletedFalse(UUID id);

    List<AuthorBook> findAllByDeletedFalse();

    boolean existsByBookId(UUID id);

    @Query(value = "SELECT * FROM author_book ORDER BY updated_at DESC LIMIT 1", nativeQuery = true)
    Optional<AuthorBook> findLast();
}
