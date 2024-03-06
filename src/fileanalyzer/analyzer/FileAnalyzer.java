package fileanalyzer.analyzer;

import fileanalyzer.model.FileAnalyzeData;
import fileanalyzer.model.FileReadData;
import fileanalyzer.util.TimeTracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileAnalyzer {
    private static final ConcurrentHashMap<UUID, FileAnalyzeData> filesAnalyzeData = new ConcurrentHashMap<>();

    public static void analyzeFiles(File[] files){
        filesAnalyzeData.clear();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (File file : files) {
            executor.submit(() -> {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    TimeTracker.start();
                    String line;
                    long characterCount = 0;
                    long wordCount = 0;
                    while ((line = reader.readLine()) != null) {
                        characterCount += line.length();
                        wordCount += line.split("\\s+").length;
                    }
                    long analyzeTime = TimeTracker.stop();
                    processFileAnalyzeData(file.getName(), file.getAbsolutePath(), characterCount, wordCount, analyzeTime);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static File[] extractFiles(ConcurrentHashMap<UUID, FileReadData> filesReadData, String fileName) {
        List<File> allFiles = new ArrayList<>();
        for (FileReadData fileReadData : filesReadData.values()) {
            File[] files = fileReadData.getFiles();
            if (files != null) {
                for (File file : files) {
                    if ("*".equals(fileName) || file.getName().equals(fileName)) {
                        allFiles.add(file);
                    }
                }
            }
        }
        return allFiles.toArray(new File[0]);
    }

    public static void processFileAnalyzeData(String fileName, String absolutePath, long characterCount, long wordCount, long analyzeTime){
        UUID analyzeFileId = UUID.randomUUID();
        FileAnalyzeData fileAnalyzeData = new FileAnalyzeData();
        fileAnalyzeData.setFileName(fileName);
        fileAnalyzeData.setAbsolutePath(absolutePath);
        fileAnalyzeData.setCharacterCount(characterCount);
        fileAnalyzeData.setWordCount(wordCount);
        fileAnalyzeData.setAnalyzeTime(analyzeTime);
        filesAnalyzeData.put(analyzeFileId, fileAnalyzeData);
    }

    public static void printAnalyzeFilesData(){
        System.out.println("\nAnalyze Data For Each File: ");
        for (UUID key : filesAnalyzeData.keySet()) {
            FileAnalyzeData fileAnalyzeData = filesAnalyzeData.get(key);
            System.out.println();
            System.out.println("File Name: " + fileAnalyzeData.getFileName());
            System.out.println("File Path: " + fileAnalyzeData.getAbsolutePath());
            System.out.println("Character Count: " + fileAnalyzeData.getCharacterCount());
            System.out.println("Word Count: " + fileAnalyzeData.getWordCount());
            System.out.println("Time Taken To Analyze File: " + fileAnalyzeData.getAnalyzeTime());
        }
    }
}
