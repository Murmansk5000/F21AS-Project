package AllException;

public class NoMatchingNameException extends Exception{
    /*
    If there is no matching name,
    a NoMatchingNameException is thrown,
    a window will pop up reminding the user to retype.
    */
    public NoMatchingNameException(){
        super("No matching name.\n");
    }
}
