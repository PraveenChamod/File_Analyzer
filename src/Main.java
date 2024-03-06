import fileanalyzer.reader.DirectoryReader;

import java.io.File;
import java.util.List;

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
    }
}