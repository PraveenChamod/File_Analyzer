package fileanalyzer.reader;

import fileanalyzer.model.FileReadData;
import fileanalyzer.util.TimeTracker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileReader {
    private static final ConcurrentHashMap<UUID, FileReadData> filesReadData = new ConcurrentHashMap<>();
    public static void readFiles(List<String> subdirectories) {
        filesReadData.clear();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String subdirectory : subdirectories) {
            File directory = new File(subdirectory);
            File[] files = directory.listFiles();
            if (directory.isDirectory() && files != null){
                executor.submit(() -> {
                    try {
                        List<String> filePaths = new ArrayList<>();
                        List<File> fileList = new ArrayList<>();
                        TimeTracker.start();
                        for (File file : files) {
                            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                                filePaths.add(file.getAbsolutePath());
                                fileList.add(file);
                            }
                        }
                        if (!fileList.isEmpty()){
                            File[] filesArray = fileList.toArray(new File[0]);
                            long readTime = TimeTracker.stop();
                            processFiles(subdirectory, filePaths, filesArray, readTime);
                        }
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                });
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void processFiles(String directoryPath, List<String> filePaths, File[] files, long readTime) {
        UUID readFileId = UUID.randomUUID();
        FileReadData fileData = new FileReadData();
        fileData.setDirectoryPath(directoryPath);
        fileData.setFilePaths(filePaths);
        fileData.setFiles(files);
        fileData.setReadTime(readTime);
        filesReadData.put(readFileId, fileData);
    }

    public static ConcurrentHashMap<UUID, FileReadData> getAllFiles() {
        return filesReadData;
    }

    public static void printFiles(){
        for (UUID key : filesReadData.keySet()) {
            FileReadData fileReadData = filesReadData.get(key);
            System.out.println();
            System.out.println("Files in the => " + fileReadData.getDirectoryPath());
            for (String filePath : fileReadData.getFilePaths()) {
                System.out.println(filePath);
            }
            System.out.println("Time Taken To Read Files: " + fileReadData.getReadTime());
        }
    }
}
