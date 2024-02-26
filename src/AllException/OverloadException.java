package AllException;

public class OverloadException extends Exception{
    /*
    If the flight is overweight,
    a OverloadException is thrown,
    reminding the staff in the flight report.
    */
    public OverloadException(String flight){
        super("models.Flight "+flight+" is overload.");
    }
}
