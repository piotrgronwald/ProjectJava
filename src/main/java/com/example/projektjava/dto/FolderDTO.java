package com.example.projektjava.dto;

public class FolderDTO {
    private Long id;
    private String name;
    private long totalSize;
    private int fileCount;
    private double averageFileSize;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize){
        this.totalSize = totalSize;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public double getAverageFileSize(){
        return averageFileSize;
    }

    public void setAverageFileSize(double averageFileSize){
        this.averageFileSize = averageFileSize;
    }

}
