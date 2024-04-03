package Stage2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Log class is responsible for managing log file creation, log generation, and writing log messages to the file.
 */
public class Log {
    /** Prefix for the log file name, used as part of building the log file name. */
    private static final String LOG_FILE_PREFIX = "file/log/check_in";
    /** Suffix for the log file, indicating the file type as .log. */
    private static final String LOG_FILE_SUFFIX = ".log";
    /** Formatter for the date-time part of the log file name, ensuring each log file name is unique. */
    private static final SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /** Formatter for the date-time in log entries, ensuring a uniform time format across log messages. */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /** The log file name generated based on the current date and time, ensuring log files can be distinguished by their creation time. */
    private static final String logFileName = LOG_FILE_PREFIX + "_" + fileDateFormat.format(new Date()) + LOG_FILE_SUFFIX;
    /** The log queue, used to store log entries that are waiting to be written to the file. */
    private static final LogQueue logQueue = new LogQueue();

    /**
     * Constructor for the Log class. It starts a new thread that continuously reads log entries from the log queue and writes them to the log file.
     */
    private Log() {
        new Thread(() -> {
            while (true) {
                String logEntry = logQueue.dequeue(); // Dequeue a log entry
                if (logEntry != null) {
                    writeLogToFile(logEntry); // Write the non-null log entry to file
                }
            }
        }).start(); // Start the thread
    }

    /**
     * Generates a log entry and adds it to the log queue.
     * The log message to be recorded
     */
    public static void generateLog(String message) {
        String timestamp = dateFormat.format(new Date()); // Get the current timestamp
        String logEntry = timestamp + " " + message; // Create the log entry
        logQueue.enqueue(logEntry); // Add the log entry to the queue
    }

    /**
     * Writes a log entry to the log file.
     * The log entry to be written
     */
    private void writeLogToFile(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(logEntry); // Write the log entry
            writer.newLine(); // Move to a new line, preparing for the next entry
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage()); // Handle exceptions during the write process
        }
    }
}
