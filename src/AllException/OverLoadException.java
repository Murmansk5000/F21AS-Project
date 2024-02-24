package AllException;

public class OverLoadException extends Exception{
    /*
    If the flight is overweight,
    a NoMatchingNameException is thrown,
    reminding the staff in the flight report.
    */
    public OverLoadException(){
        super("Flight xxx is overload.");
    }
}
