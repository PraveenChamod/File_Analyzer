package fileanalyzer.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DirectoryReader {
    private static final List<String> subDirectories = new ArrayList<>();
    public static void readSubDirectories(String directoryPath) {
        Path startDir = Paths.get(directoryPath);
        try {
            ExecutorService executor = Executors.newFixedThreadPool(10);

            Files.walk(startDir)
                    .filter(Files::isDirectory)
                    .forEach(directory -> executor.submit(() -> processDirectory(directory)));

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void processDirectory(Path directory) {
        subDirectories.add(directory.toString());
    }

    public static List<String> getSubDirectories() {
        return subDirectories;
    }

    public static void printDirectories(){
        System.out.println();
        for(String subDirectory : subDirectories){
            System.out.println(subDirectory);
        }
        System.out.println();
    }
}
