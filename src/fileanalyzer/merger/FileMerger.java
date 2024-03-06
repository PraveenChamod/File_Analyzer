package fileanalyzer.merger;

import fileanalyzer.model.FileMergeData;
import fileanalyzer.model.FileReadData;
import fileanalyzer.util.TimeTracker;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileMerger {
    private static final ConcurrentHashMap<UUID, FileMergeData> filesMergeData = new ConcurrentHashMap<>();

    public static void mergeFilesInEachDirectory(ConcurrentHashMap<UUID, FileReadData> mergeData){
        filesMergeData.clear();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (FileReadData fileReadData : mergeData.values()) {
            executor.submit(() -> mergeFiles(fileReadData.getFiles(), fileReadData.getDirectoryPath()));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void mergeFiles(File[] files, String directory){
        if(files.length > 1){
            String mergedFilePath = directory + File.separator + "merged_file.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(mergedFilePath))) {
                TimeTracker.start();
                for (File file : files) {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                    reader.close();
                }
                long mergeTime = TimeTracker.stop();
                processFileMergeData(directory, mergeTime);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void processFileMergeData(String directoryPath, long mergeTime){
        UUID mergeFileId = UUID.randomUUID();
        FileMergeData fileMergeData = new FileMergeData();
        fileMergeData.setMergeFileName("merged_file");
        fileMergeData.setDirectoryPath(directoryPath);
        fileMergeData.setMergeTime(mergeTime);
        filesMergeData.put(mergeFileId, fileMergeData);
    }
}
