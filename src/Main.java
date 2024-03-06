import fileanalyzer.analyzer.FileAnalyzer;
import fileanalyzer.merger.FileMerger;
import fileanalyzer.model.FileReadData;
import fileanalyzer.reader.DirectoryReader;
import fileanalyzer.reader.FileReader;
import fileanalyzer.util.TimeTracker;

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

        TimeTracker.start();
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

        //Merge files in each directory and merge to one file in each directory
        FileMerger.mergeFilesInEachDirectory(filesReadData);

        //Analyze character count, word count, file name and absolute path to file for each merged file
        FileReader.readFiles(subdirectories);
        ConcurrentHashMap<UUID, FileReadData> filesReadDataAfterMerge = FileReader.getAllFiles();
        File[] mergeFiles = FileAnalyzer.extractFiles(filesReadDataAfterMerge, "merged_file.txt");
        FileAnalyzer.analyzeFiles(mergeFiles);

        //Print character count, word count, file name and absolute path to file for each merged file
        FileAnalyzer.printAnalyzeFilesData();

        //Write each merged file in to a file named ‘merged file’ in particular directory
        FileMerger.mergeFiles(mergeFiles, directoryPath);
        long TotalTime = TimeTracker.stop();
        System.out.println("Time Taken For Whole Process : " + TotalTime);
    }
}