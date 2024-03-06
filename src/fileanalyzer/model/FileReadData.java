package fileanalyzer.model;

import java.io.File;
import java.util.List;

public class FileReadData {
    private String directoryPath;
    private List<String> FilePaths;
    private File[] Files;
    private long ReadTime;

    public FileReadData() { }
    public List<String> getFilePaths() {
        return FilePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        FilePaths = filePaths;
    }

    public long getReadTime() {
        return ReadTime;
    }

    public void setReadTime(long timeToFindFiles) {
        ReadTime = timeToFindFiles;
    }

    public String getDirectoryPath() { return directoryPath; }

    public void setDirectoryPath(String directoryPath) { this.directoryPath = directoryPath; }

    public File[] getFiles() { return Files; }

    public void setFiles(File[] files) { Files = files; }
}
