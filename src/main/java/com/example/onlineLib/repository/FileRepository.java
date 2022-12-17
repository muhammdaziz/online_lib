package com.example.onlineLib.repository;

import com.example.onlineLib.entity.FileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileImg, UUID> {
}
