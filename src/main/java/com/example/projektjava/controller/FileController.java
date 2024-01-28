package com.example.projektjava.controller;

import com.example.projektjava.dto.FolderHierarchyDTO;
import com.example.projektjava.dto.ReportDTO;
import com.example.projektjava.model.FileEntity;
import com.example.projektjava.repository.FileRepository;
import com.example.projektjava.model.FolderEntity;
import com.example.projektjava.repository.FolderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    @GetMapping
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    @GetMapping("/{id}")
    public FileEntity getFileById(@PathVariable Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @GetMapping("/folders")
    public List<FolderEntity> getAllFolders() {
        return folderRepository.findAll();
    }

    @GetMapping("/folders/{id}")
    public FolderEntity getFolderById(@PathVariable Long id) {
        return folderRepository.findById(id).orElse(null);
    }

    @GetMapping("/filesInFolder/{folderId}")
    public List<FolderEntity> getFilesInFolder(@PathVariable Long folderId) {
        FolderEntity folder = folderRepository.findById(folderId).orElse(null);
        if (folder != null) {
            return folder.getFiles();
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/reports")
    public ResponseEntity<ReportDTO> generateReport() {
        ReportDTO report = new ReportDTO();

        report.setTotalSize(fileRepository.calculateTotalSize());
        report.setAverageFileSize(fileRepository.calculateAverageFileSize());
        report.setHighestFileSize(fileRepository.findHighestFileSize());
        report.setLowestFileSize(fileRepository.findLowestFileSize());
        report.setNumberOfFiles(fileRepository.count());

        int topN = 5;
        report.setTopFoldersBySize(folderRepository.findTopFoldersBySize((Pageable) PageRequest.of(0, topN)));
        report.setTopFoldersByFileCount(folderRepository.findTopFoldersByFileCount((Pageable) PageRequest.of(0, topN)));
        report.setTopFoldersByAverageFileSize(folderRepository.findTopFoldersByAverageFileSize((Pageable) PageRequest.of(0, topN)));

        report.setDeepestFolderHierarchy(generateDeepestFolderHierarchy((Pageable) PageRequest.of(0, 10)));


        return ResponseEntity.ok(report);
    }

    private FolderHierarchyDTO generateDeepestFolderHierarchy(Pageable pageable) {
        List<FolderEntity> allFolders = folderRepository.findAll((org.springframework.data.domain.Pageable) pageable).getContent();
        FolderEntity deepestFolder = findDeepestFolder(allFolders);

        if (deepestFolder != null) {
            FolderHierarchyDTO folderHierarchyDTO = new FolderHierarchyDTO();
            folderHierarchyDTO.setFolderName(deepestFolder.getName());
            folderHierarchyDTO.setSubFolders(generateSubFolderHierarchy(deepestFolder.getSubFolders(), pageable));
            return folderHierarchyDTO;
        }

        return null;
    }

    private List<FolderHierarchyDTO> generateSubFolderHierarchy(List<FolderEntity> subFolders, Pageable pageable) {
        List<FolderHierarchyDTO> subFolderHierarchy = new ArrayList<>();

        for (FolderEntity subFolder : subFolders) {
            FolderHierarchyDTO subFolderDTO = new FolderHierarchyDTO();
            subFolderDTO.setFolderName(subFolder.getName());
            subFolderDTO.setSubFolders(generateSubFolderHierarchy(subFolder.getSubFolders(), pageable));
            subFolderHierarchy.add(subFolderDTO);
        }

        return subFolderHierarchy;
    }


    private FolderEntity findDeepestFolder(List<FolderEntity> folders) {
        Map<Long, Set<Long>> folderHierarchy = new HashMap<>();
        Set<Long> allFolderIds = new HashSet<>();

        for (FolderEntity folder : folders) {
            Set<Long> subFoldersIds = folder.getSubFolders().stream().map(FolderEntity::getId).collect(Collectors.toSet());
            folderHierarchy.put(folder.getId(), subFoldersIds);
            allFolderIds.add(folder.getId());
            allFolderIds.addAll(subFoldersIds);
        }

        for (Long folderId : allFolderIds) {
            boolean hasChildren = folderHierarchy.values().stream().anyMatch(children -> children.contains(folderId));
            if (!hasChildren) {
                return folderRepository.findById(folderId).orElse(null);
            }
        }
        return null;
    }


    @PostMapping
    public FileEntity createFile(@RequestBody FileEntity fileEntity) {
        if (fileEntity.getFolder() != null && fileEntity.getFolder().getId() != null) {
            FolderEntity existingFolder = folderRepository.findById(fileEntity.getFolder().getId()).orElse(null);
            fileEntity.setFolder(existingFolder);
        }
        return fileRepository.save(fileEntity);
    }

    @PutMapping("/{id}")
    public FileEntity updateFile(@PathVariable Long id, @RequestBody FileEntity updatedFile) {
        FileEntity existingFile = fileRepository.findById(id).orElse(null);

        if (existingFile != null) {
            BeanUtils.copyProperties(updatedFile, existingFile, "id", "folder");

            if (updatedFile.getFolder() != null && updatedFile.getFolder().getId() != null) {
                FolderEntity existingFolder = folderRepository.findById(updatedFile.getFolder().getId()).orElse(null);
                existingFile.setFolder(existingFolder);
            }

            return fileRepository.save(existingFile);
        } else {
            return null;
        }
    }

    @PostMapping("/folders")
    public FolderEntity createFolder(@RequestBody FolderEntity folderEntity){
        return folderRepository.save(folderEntity);
    }

    @PutMapping("/folders/{id}")
    public FolderEntity updateFolder(@PathVariable Long id, @RequestBody FolderEntity updatedFolder){
        FolderEntity existingFolder = folderRepository.findById(id).orElse(null);

        if(existingFolder != null) {
            BeanUtils.copyProperties(updatedFolder, existingFolder, "id", "subFolders");
            return folderRepository.save(existingFolder);
        }else{
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileRepository.deleteById(id);
    }

    @DeleteMapping("/folders/{id}")
    public void deleteFolder(@PathVariable Long id){
        folderRepository.deleteById(id);
    }
}
