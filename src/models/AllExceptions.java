package models;

import javax.swing.*;

public class AllExceptions extends Exception {
    /*
    If the input format (information of checked baggage) is incorrect,
    a FormatErrorException is thrown, a window will pop up reminding the user to retype.
    */
    public static class FormatErrorException extends Exception {
        public FormatErrorException() {
            super("Incorrect input format of checked baggage.\n" +
                    "                                           Dimensions of Checked baggage: the sum of length, width, and height not exceeding 158 (cm).\n" +
                    "                                           Weight of Checked baggage: not exceeding 23 (kg).");
            JOptionPane.showMessageDialog(null, "Incorrect input format.\n" +
                    "Dimensions of Checked baggage: the sum of length, width, and height not exceeding 158 (cm).\n" +
                    "Weight of Checked baggage: not exceeding 23 (kg).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
    If there is no matching name,
    a NoMatchingNameException is thrown,
    a window will pop up reminding the user to retype.
    */
    public static class NoMatchingNameException extends Exception {
        public NoMatchingNameException(String name) {
            super("There is no matching last name: " + name + ", please check.");
            JOptionPane.showMessageDialog(null, "There is no matching last name: " + name + ", please check.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
    If there is no matching booking reference code,
    a NoMatchingRefException is thrown,
    a window will pop up reminding the user to retype.
    */
    public static class NoMatchingRefException extends Exception {
        public NoMatchingRefException(String ref) {
            super("There is no matching reference number: " + ref + ", please check.");
            JOptionPane.showMessageDialog(null, "There is no matching reference number: " + ref + ", please check.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
    If one of the baggage information is blank,
    an IncompleteBaggageInfoException is thrown,
    a window will pop up reminding the passenger to complete the four field of the baggage.
    */
    public static class IncompleteBaggageInfoException extends Exception {
        public IncompleteBaggageInfoException() {
            super("There has blank field for the baggage information, please complete.");
            JOptionPane.showMessageDialog(null, "There has blank field for the baggage information, please complete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
    If one/two/three of the baggage information is 0,
    an InvalidBaggageInfoException is thrown,
    a window will pop up reminding the passenger to re-enter.
    */
    public static class InvalidBaggageInfoException extends Exception {
        public InvalidBaggageInfoException() {
            super("Some of the baggage weight and volume information you entered is 0, please check.\n" +
                    "                                                  (Excluded cases where all of these information is 0)");
            JOptionPane.showMessageDialog(null, "Some of the baggage weight and volume information you entered is 0, please check.\n" +
                    "(Excluded cases where all of these information is 0)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
    If the baggage exceeds the rated baggage limit,
    a OverWeightException is thrown,
    a window will pop up asking the passenger
    if they would like to purchase the excess baggage allowance.
    */
    public static class OverWeightException extends Exception {
        public OverWeightException() {
            super("The baggage exceeds the rated baggage limit(40kg).");
            //JOptionPane.showMessageDialog(null, "The baggage exceeds the rated baggage limit(23kg).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
    If cannot find the matching flight,
    reminding the passenger.
    */
    public static class NoMatchingFlightException extends Exception {
        public NoMatchingFlightException() {
            super("There is no matching flight information for you.");
            JOptionPane.showMessageDialog(null, "There is no matching flight information for you.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public static void AllException(Exception e) {
//        if (e instanceof FormatErrorException) {
//            // 显示特定的异常窗口
//        }
//        if (e instanceof NoMatchingNameException) {
//            // 显示特定的异常窗口
//        }
//        if (e instanceof NoMatchingRefException) {
//            // 显示特定的异常窗口
//        }
//        if (e instanceof OverWeightException) {
//            // 显示特定的异常窗口
//        }
//        if (e instanceof OverloadException) {
//            // 显示特定的异常窗口
//        }else {
//            // 显示一般的异常窗口或处理其他类型的异常
//        }
//    }
}

