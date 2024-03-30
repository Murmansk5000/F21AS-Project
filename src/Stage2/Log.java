package Stage2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Log {

    private static final String LOG_FILE_PREFIX = "check_in"; // file prefix
    private static final String LOG_FILE_SUFFIX = ".log"; // file suffix
    private static final long MAX_LOG_FILE_SIZE = 1024 * 1024; // log capacity
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final ExecutorService executor = Executors.newSingleThreadExecutor(); // 单线程执行器

    // Log module
    public static void generateLog(String message) {
        executor.submit(() -> {
            String timestamp = dateFormat.format(new Date());
            String logEntry = timestamp + " " + message;
            writeLog(logEntry);
        });
    }

    // Write log to file
    private static synchronized void writeLog(String logEntry) {
        try {
            File logFile = new File(LOG_FILE_PREFIX + LOG_FILE_SUFFIX);
            // Log rotation
            if (logFile.exists() && logFile.length() > MAX_LOG_FILE_SIZE) {
                File newLogFile = new File(LOG_FILE_PREFIX + "_" + System.currentTimeMillis() + LOG_FILE_SUFFIX);
                logFile.renameTo(newLogFile);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(logEntry);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Log.generateLog("This is an info message.");
        Log.generateLog("This is another log entry.");
    }

}
