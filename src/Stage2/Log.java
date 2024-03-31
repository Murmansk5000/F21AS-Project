package Stage2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final String LOG_FILE_PREFIX = "file/log/check_in";
    private static final String LOG_FILE_SUFFIX = ".log";
    private static final SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String logFileName = LOG_FILE_PREFIX + "_" + fileDateFormat.format(new Date()) + LOG_FILE_SUFFIX;
    private static final LogQueue logQueue = new LogQueue();

    public Log() {
        new Thread(() -> {
            while (true) {
                String logEntry = logQueue.dequeue();
                if (logEntry != null) {
                    writeLogToFile(logEntry);
                }
            }
        }).start();
    }

    public static void generateLog(String message) {
        String timestamp = dateFormat.format(new Date());
        String logEntry = timestamp + " " + message;
        logQueue.enqueue(logEntry);
    }

    private void writeLogToFile(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}