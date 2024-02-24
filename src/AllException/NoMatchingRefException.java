package AllException;

public class NoMatchingRefException extends Exception{
    /*
    If there is no matching booking reference code,
    a NoMatchingRefException is thrown,
    a window will pop up reminding the user to retype.
    */
    public NoMatchingRefException(String ref){
        super("There is no "+ref+", please check and re-enter.");
    }
}
