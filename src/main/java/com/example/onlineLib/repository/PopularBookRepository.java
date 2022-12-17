package com.example.onlineLib.repository;

import com.example.onlineLib.entity.PopularBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PopularBookRepository extends JpaRepository<PopularBook, UUID> {
    Optional<PopularBook> findByIdAndDeletedFalse(UUID id);

    List<PopularBook> findAllByDeletedFalse();

    boolean existsByBookId(UUID bookId);

    @Query(value = "SELECT * FROM popular_book WHERE deleted = false ORDER BY updated_at DESC LIMIT 1",nativeQuery = true)
    Optional<PopularBook> findMostPopular();
}
