public class Passenger implements Comparable<Passenger> {
    private String referenceCode;
    private String firstName;
    private String lastName;
    private String flightCode;
    private boolean ifCheck;
    private BaggageList baggagesOfPassenger;

    /**
     * Constructor for Passenger that sets up the passenger details. All string details are trimmed to remove
     * trailing white space.
     *
     * @param referenceCode The unique reference code for the passenger.
     * @param firstName     The first name of the passenger.
     * @param lastName      The last name of the passenger.
     * @param flightCode    The code of the flight associated with the passenger.
     * @param ifCheck       Boolean flag indicating whether the passenger has checked in.
     */
    public Passenger(String referenceCode, String firstName, String lastName, String flightCode, boolean ifCheck) {
        this.referenceCode = referenceCode.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.flightCode = flightCode.trim();
        this.ifCheck = ifCheck;
        this.baggagesOfPassenger = new BaggageList(); // Initialize the baggage list for the passenger
    }

    /**
     * @return The unique reference code of the passenger.
     */
    public String getRefCode() {
        return referenceCode;
    }

    /**
     * @return The first name of the passenger.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return The last name of the passenger.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return The flight code associated with the passenger.
     */
    public String getFlightCode() {
        return flightCode;
    }

    /**
     * @return True if the passenger has checked in, false otherwise.
     */
    public boolean getIfCheck() {
        return ifCheck;
    }

    /**
     * Test for content equality between two objects.
     *
     * @param other The object to compare to this one.
     * @return true if the argument object is a Passenger and has the same reference code.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Passenger passenger = (Passenger) other;
        return referenceCode.equals(passenger.referenceCode);
    }

    /**
     * @return A string containing the passenger's reference code, first name, and last name.
     */
    @Override
    public String toString() {
        return "Passenger{" +
                "refCode='" + referenceCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", flightCode='" + flightCode + '\'' +
                ", checkedIn=" + ifCheck +
                '}';
    }

    /**
     * Generate a hash code for this passenger.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return referenceCode.hashCode();
    }


    @Override
    public int compareTo(Passenger o) {
        return 0;
    }
}
