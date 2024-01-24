package com.example.projektjava.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_folder_id")
    private List<FolderEntity> subFolders;

    @OneToMany(mappedBy = "folder")
    private List<FileEntity> files;

    @OneToMany
    private List<FolderEntity> Files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FolderEntity> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<FolderEntity> subFolders) {
        this.subFolders = subFolders;
    }

    public List<FolderEntity> getFiles(){
        return Files;
    }

    public void setFiles(List<FolderEntity> Files){
        this.Files = Files;
    }
}

