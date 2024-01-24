package com.example.projektjava.dto;

public class ReportDTO {
    private long totalSize;
    private long highestFileSize;
    private long lowestFileSize;
    private long numberOfFiles;

    private double averageFileSize;

    private List<FolderDTO> topFoldersBySize;
    private List<FolderDTO> topFoldersByFileCount;
    private List<FolderDTO> topFoldersByAverageFileSize;

    private FolderHierarchyDTO deepestFolderHierarchy;

    public long getTotalSize()  {
        return totalSize;
    }

    public void setTotalSize(long totalSize){
        this.totalSize = totalSize;
    }

    public double getAverageFileSize(){
        return averageFileSize;
    }

    public void setAverageFileSize(double averageFileSize)   {
        this.averageFileSize = averageFileSize;
    }

    public long getHighestFileSize() {
        return highestFileSize;
    }

    public void setHighestFileSize(long highestFileSize){
        this.highestFileSize = highestFileSize;
    }

    public long getLowestFileSize() {
        return lowestFileSize;
    }

    public void setLowestFileSize(long lowestFileSize)  {
        this.lowestFileSize = lowestFileSize;
    }

    public long getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(long numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public List<FolderDTO> getTopFoldersBySize() {
        return topFoldersBySize;
    }

    public void setTopFoldersBySize(List<FolderDTO> topFoldersBySize)  {
        this.topFoldersBySize = topFoldersBySize;
    }

    public List<FolderDTO> getTopFoldersByFileCount() {
        return topFoldersByFileCount;
    }

    public void setTopFoldersByFileCount(List<FolderDTO> topFoldersByFileCount) {
        this.topFoldersByFileCount = topFoldersByFileCount;
    }

    public List<FolderDTO> getTopFoldersByAverageFileSize() {
        return topFoldersByAverageFileSize;
    }

    public void setTopFoldersByAverageFileSize(List<FolderDTO> topFoldersByAverageFileSize) {
        this.topFoldersByAverageFileSize = topFoldersByAverageFileSize;
    }

    public FolderHierarchyDTO getDeepestFolderHierarchy() {
        return deepestFolderHierarchy;
    }

    public void setDeepestFolderHierarchy(FolderHierarchyDTO deepestFolderHierarchy) {
        this.deepestFolderHierarchy = deepestFolderHierarchy;
    }

}
