package AllException;

public class NoMatchingRefException extends Exception{
    /*
    If there is no matching name,
    a NoMatchingNameException is thrown,
    a window will pop up reminding the user to retype.
    */
    public NoMatchingRefException(){
        super("There is no matching reference code, please re-enter.");
    }
}
