package com.example.projektjava.dto;
import java.util.List;
public class FolderHierarchyDTO {
    private String folderName;
    private List<FolderHierarchyDTO> subFolders;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName){

        this.folderName = folderName;
    }

    public List<FolderHierarchyDTO> getSubFolders() {

        return subFolders;
    }

    public void setSubFolders(List<FolderHierarchyDTO> subFolders){

        this.subFolders = subFolders;
    }
}
