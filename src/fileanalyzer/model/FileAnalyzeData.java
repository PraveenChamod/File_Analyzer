package fileanalyzer.model;

public class FileAnalyzeData {
    private String fileName;
    private String absolutePath;
    private long characterCount;
    private long wordCount;
    private long analyzeTime;

    public FileAnalyzeData() { }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String filePath) {
        this.absolutePath = filePath;
    }

    public long getCharacterCount() {
        return characterCount;
    }

    public void setCharacterCount(long characterCount) {
        this.characterCount = characterCount;
    }

    public long getWordCount() {
        return wordCount;
    }

    public void setWordCount(long wordCount) {
        this.wordCount = wordCount;
    }

    public long getAnalyzeTime() {return analyzeTime;}

    public void setAnalyzeTime(long analyzeTime) {this.analyzeTime = analyzeTime;}
}
