package Stage2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Log {

    private static final String LOG_FILE_PREFIX = "file/log/check_in"; // file prefix
    private static final String LOG_FILE_SUFFIX = ".log"; // file suffix
    //private static final long MAX_LOG_FILE_SIZE = 1024 * 1024; // log capacity
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String logFileName = LOG_FILE_PREFIX + "_" + fileDateFormat.format(new Date()) + LOG_FILE_SUFFIX;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor(); // 单线程执行器

    // Log module
    public static synchronized void generateLog(String message) {
        executor.submit(() -> {
            String timestamp = dateFormat.format(new Date());
            String logEntry = timestamp + " " + message;
            writeLog(logEntry);
        });
    }

    // Write log to file
    private static synchronized void writeLog(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }

    }

    public static void shutdown() {
        executor.shutdown();
    }
}
