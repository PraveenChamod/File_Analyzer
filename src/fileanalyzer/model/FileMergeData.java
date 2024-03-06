package fileanalyzer.model;

public class FileMergeData {
    private String mergeFileName;
    private String directoryPath;
    private long mergeTime;

    public FileMergeData() { }

    public String getMergeFileName() {
        return mergeFileName;
    }

    public void setMergeFileName(String mergeFileName) {
        this.mergeFileName = mergeFileName;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public long getMergeTime() {
        return mergeTime;
    }

    public void setMergeTime(long mergeTime) {
        this.mergeTime = mergeTime;
    }
}
