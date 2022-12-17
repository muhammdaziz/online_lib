package com.example.onlineLib.repository;

import com.example.onlineLib.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByIdAndDeletedFalse(Integer id);

    List<Category> findAllByDeletedFalse();

    boolean existsByName(String name);
}
