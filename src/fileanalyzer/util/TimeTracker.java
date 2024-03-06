package fileanalyzer.util;

public class TimeTracker {
    private static long startTime;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static long stop() {
        return System.currentTimeMillis() - startTime;
    }
}
