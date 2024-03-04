package models;

public class Passenger implements Comparable<Passenger> {
    private String referenceCode;
    private String firstName;
    private String lastName;
    private String flightCode;
    private boolean ifCheck;
    private BaggageList baggagesOfPassenger;

    /**
     * Construct a new models.Passenger with the specified details. All provided information is used
     * to initialize the passenger's state.
     *
     * @param referenceCode The unique reference code for the passenger.
     * @param firstName     The first name of the passenger.
     * @param lastName      The last name of the passenger.
     * @param flightCode    The code of the flight associated with the passenger.
     * @param ifCheck       Boolean flag indicating whether the passenger has checked in.
     */
    public Passenger(String referenceCode, String firstName, String lastName, String flightCode, boolean ifCheck) {
        //这个异常写在ShowGUI里了，这边的不知道要不要删掉
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


    /**
     * @return A string containing the passenger's reference code, first name, and last name.
     */
    @Override
    public String toString() {
        return "models.Passenger{" +
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

    public BaggageList getBaggageList() {
        return this.baggagesOfPassenger;
    }

    public void setBaggageList(BaggageList baggageList) {
        this.baggagesOfPassenger = baggageList;
    }
    public void setIfCheck(boolean ifCheck){
        this.ifCheck = ifCheck;
    }


}