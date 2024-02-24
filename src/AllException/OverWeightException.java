package AllException;

public class OverWeightException extends Exception{
    /*
    If the baggage exceeds the rated baggage limit,
    a OverWeightException is thrown,
    a window will pop up asking the passenger if they
    would like to purchase the excess baggage allowance.
    */
    public OverWeightException(){
        super("The baggage exceeds the rated baggage limit.\n"+
                "Do you want to buy baggage allowance for the excess?\n");
    }
}
