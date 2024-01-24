package com.example.projektjava.repository;

import com.example.projektjava.dto.FolderDTO;
import com.example.projektjava.model.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;

public interface FolderRepository extends JpaRepository<FolderEntity, Long> {

    @Query("SELECT new com.example.projektjava.dto.FolderDTO(f.id, f.name, SUM(file.size), COUNT(file), AVG(file.size)) ") +
           "FROM FolderEntity f " +
           "LEFT JOIN f.files file " +
           "GROUP BY f.id, f.name")
    List<FolderDTO> findTopFoldersBySize(Pageable pageable);

    @Query("SELECT new com.example.projektjava.dto.FolderDTO(f.id, f.name, SUM(file.size), COUNT(file), AVG(file.size)) " +
            "FROM FolderEntity f " +
            "LEFT JOIN f.files file " +
            "GROUP BY f.id, f.name " +
            "ORDER BY COUNT(file) DESC")
    List<FolderDTO> findTopFoldersByFileCount(Pageable pageable);

    @Query("SELECT new com.example.projektjava.dto.FolderDTO(f.id, f.name, SUM(file.size), COUNT(file), AVG(file.size)) " +
            "FROM FolderEntity f " +
            "LEFT JOIN f.files file " +
            "GROUP BY f.id, f.name " +
            "ORDER BY AVG(file.size) DESC")
    List<FolderDTO> findTopFoldersByAverageFileSize(Pageable pageable);
}
