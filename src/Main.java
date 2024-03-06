import java.io.File;

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
    }
}