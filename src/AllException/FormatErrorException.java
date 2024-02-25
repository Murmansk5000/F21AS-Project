package AllException;

public class FormatErrorException extends Exception{
    /*
    If the input format (information of checked baggage) is incorrect,
    a FormatErrorException is thrown, a window will pop up reminding the user to retype.
    */
    public FormatErrorException(){
        super("Incorrect input format.\n" +
                "Dimensions of Checked baggage: the sum of length, width, and height not exceeding 158 (cm).\n" +
                "Weight of Checked baggage: not exceeding 23 (kg).");
    }
}
