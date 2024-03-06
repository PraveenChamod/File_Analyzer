import fileanalyzer.analyzer.FileAnalyzer;
import fileanalyzer.model.FileReadData;
import fileanalyzer.reader.DirectoryReader;
import fileanalyzer.reader.FileReader;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <directory-path>");
            return;
        }
        String directoryPath = args[0];
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return;
        }

        //Read Sub Directories
        DirectoryReader.readSubDirectories(directoryPath);
        List<String> subdirectories = DirectoryReader.getSubDirectories();
        DirectoryReader.printDirectories();

        //Reads the list of files in each subdirectory and print separately
        FileReader.readFiles(subdirectories);
        ConcurrentHashMap<UUID, FileReadData> filesReadData = FileReader.getAllFiles();
        FileReader.printFiles();

        //Analyze each file and extract character count and word counts separately
        File[] files = FileAnalyzer.extractFiles(filesReadData, "*");
        FileAnalyzer.analyzeFiles(files);

        //Print character count, word count , file name, absolute path to file
        FileAnalyzer.printAnalyzeFilesData();
    }
}