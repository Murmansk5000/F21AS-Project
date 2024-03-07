package models;

import javax.swing.*;

public class AllExceptions extends Exception {
    /**
     * If the input number (information of checked baggage) is exceeds the limits,
     * a NumberErrorException is thrown, a window will pop up reminding the user to retype.
     */
    public static class NumberErrorException extends Exception {
        public NumberErrorException() {
            super("""
                    Incorrect input number of checked baggage.
                                                               Dimensions of Checked baggage: the sum of length, width, and height not exceeding 158 (cm).
                                                               Weight of Checked baggage: not exceeding 23 (kg).""");
            JOptionPane.showMessageDialog(null, "Incorrect input number.\n" +
                    "Dimensions of Checked baggage: the sum of length, width, and height not exceeding 158 (cm).\n" +
                    "Weight of Checked baggage: not exceeding 23 (kg).\n", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Exception thrown when the provided name and reference code do not match.
     * A NameCodeMismatchException is thrown to indicate the mismatch.
     */
    public static class NameCodeMismatchException extends Exception {
        /**
         * Constructs a NameCodeMismatchException with the specified reference code and name.
         *
         * @param ref  The reference code.
         * @param name The name associated with the reference code.
         */
        public NameCodeMismatchException(String ref, String name) {
            super("Mismatch between reference code: " + ref + " and the name: " + name + ", please check.");
            JOptionPane.showMessageDialog(null, "Mismatch between reference code: " + ref + " and the name: " + name + ", please check.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * If there is no matching booking reference code,
     * a NoMatchingRefException is thrown,
     * a window will pop up reminding the user to retype.
     */
    public static class NoMatchingRefException extends Exception {
        public NoMatchingRefException(String ref) {
            super("There is no matching reference number: " + ref + ", please check.");
            JOptionPane.showMessageDialog(null, "There is no matching reference number: " + ref + ", please check.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * If one of the baggage information is blank,
     * an IncompleteBaggageInfoException is thrown,
     * a window will pop up reminding the passenger to complete the four field of the baggage.
     */
    public static class IncompleteBaggageInfoException extends Exception {
        public IncompleteBaggageInfoException() {
            super("There has blank field for the baggage information, please complete.");
            JOptionPane.showMessageDialog(null, "There has blank field for the baggage information, please complete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * If one/two/three of the baggage information is 0,
     * an InvalidBaggageInfoException is thrown,
     * a window will pop up reminding the passenger to re-enter.
     */
    public static class InvalidBaggageInfoException extends Exception {
        public InvalidBaggageInfoException() {
            super("Some of the baggage weight and volume information you entered is 0, please check.\n" +
                    "                                                  (Excluded cases where all of these information is 0)");
            JOptionPane.showMessageDialog(null, "Some of the baggage weight and volume information you entered is 0, please check.\n" +
                    "(Excluded cases where all of these information is 0)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * If the system cannot find the matching flight,
     * reminding the passenger.
     */
    public static class NoMatchingFlightException extends Exception {
        public NoMatchingFlightException() {
            super("There is no matching flight information for you.");
            JOptionPane.showMessageDialog(null, "There is no matching flight information for you.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

