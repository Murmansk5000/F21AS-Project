//a simple class to contain and manage Passenger details
//(id, name, hours)
public class Passenger implements Comparable<Passenger>
{
	private String referenceCode;
    private String firstName;
    private String lastName;
    private String flightCode;
    private boolean ifCheck;
    private BaggageList baggagesOfPassenger;

    /**
     * Set up the contact details. All details are trimmed to remove
     * trailing white space.
     * @param b 
     * @param name The name.
     * @param hoursWorked The hours worked
     */
    public Passenger(String referenceCode, String firstName, String lastName, String flightCode, boolean ifCheck)
    {   
        this.referenceCode =referenceCode.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.flightCode = flightCode.trim();
    }
    
    /**
     * @return The id.
     */    
    public String getRefCode() {
    	return referenceCode;
    }
    
    /**
     * @return The name.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @return The hours Worked
     */
    public String getLastName()
    {
        return  lastName;
    }

    public String getFlightCode()
    {
        return  flightCode;
    }
    
    public boolean getIfCheck()
    {
        return ifCheck;
    }
    
    /**
     * Test for content equality between two objects.
     * @param other The object to compare to this one.
     * @return true if the argument object has same id
     */
    public boolean equals(Object other)
    {
        if(other instanceof Passenger) {
        	Passenger otherStaff = (Passenger) other;
            return referenceCode.equals(otherStaff.getRefCode());
        }
        else {
            return false;
        }
    }


    /**
     * @return A  string containing all details.
     */
    public String toString()
    {
        return String.format("%-5s", referenceCode ) + String.format("%-20s", firstName) + String.format("%-20s", lastName) + 
                 String.format("%5d", flightCode );
    }

	@Override
	public int compareTo(Passenger o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
