public class Passenger implements Comparable<Passenger> {
    private String referenceCode;
    private String firstName;
    private String lastName;
    private String flightCode;
    private boolean ifCheck;
    private BaggageList baggagesOfPassenger;

    /**
     * Construct a new Passenger with the specified details. All provided information is used
     * to initialize the passenger's state.
     *
     * @param referenceCode The unique reference code for the passenger.
     * @param firstName     The first name of the passenger.
     * @param lastName      The last name of the passenger.
     * @param flightCode    The code of the flight associated with the passenger.
     * @param ifCheck       Boolean flag indicating whether the passenger has checked in.
     */
    public Passenger(String referenceCode, String firstName, String lastName, String flightCode, boolean ifCheck) {
        if( firstName.trim().length() ==0|| lastName.trim().length()== 0|| referenceCode.trim().length()== 0)
        {
            throw new IllegalStateException(
                    "Cannot have blank name or reference code");
        }
        this.referenceCode = referenceCode.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.flightCode = flightCode.trim();
        this.ifCheck = ifCheck;
        this.baggagesOfPassenger = new BaggageList(); // Initialize the baggage list for the passenger
    }



    public String getRefCode() {
        return referenceCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public boolean getIfCheck() {
        return ifCheck;
    }

//    /**
//     * Test for content equality between two objects.
//     *
//     * @param other The object to compare to this one.
//     * @return true if the argument object is a Passenger and has the same reference code.
//     */
//    @Override
//    public boolean equals(Object other) {
//        if (this == other) return true;
//        if (other == null || getClass() != other.getClass()) return false;
//        Passenger passenger = (Passenger) other;
//        return referenceCode.equals(passenger.referenceCode);
//    }

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

    @Override
    public int compareTo(Passenger other) {
        return this.referenceCode.compareTo(other.referenceCode);
    }
}