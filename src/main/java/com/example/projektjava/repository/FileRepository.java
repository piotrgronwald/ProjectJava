package com.example.projektjava.repository;

import com.example.projektjava.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    @Query("SELECT SUM(f.size) FROM FileEntity f")
    long calculateTotalSize();

    @Query("SELECT AVG(f.size) FROM FileEntity f")
    double calculateAverageFileSize();

    @Query("SELECT MAX(f.size) FROM FileEntity f")
    long findHighestFileSize();

    @Query("SELECT MIN(f.size) FROM FileEntity f")
    long findLowestFileSize();
}
