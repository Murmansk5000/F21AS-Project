//maintains a list of Passenger objects as an ArrayList

import java.util.ArrayList;
import java.util.Collections;

public class PassengerList
{
    // Storage for an arbitrary number of details.
    private ArrayList <Passenger> passengerList;

    /**
     * Perform any initialization for the address book.
     */
    public PassengerList()
    {
    	passengerList = new ArrayList<Passenger>() ;
    }
    
    /**
     * Look up an id and return the
     * corresponding staff details.
     * @param idThe id  to be looked up.
     * @return The details corresponding to the id, null if none
     */
    public Passenger findByRefCode(String referenceCode)
    {
    	for (Passenger p : passengerList)
    	{
    		if (p.getRefCode().equals(referenceCode))
    		{
    			return p;
    		}
    	}
    	return null;
    }
    


    /**
     * Add a new set of details to the list
     * @param details The details of the staff
     */
    public void addDetails(Passenger details) 
    {
		passengerList.add(details);
    }
    
    /**
     * remove Staff object identified by this ID
     * @param id the ID identifying the person to be removed
     */
    public void removeDetails(String id) {
    	int index = findIndex(id);
        if (index != -1) {
        	passengerList.remove(index);
        }
    }
    /**
     * Look up an id and return index
     * @param id The id  to be looked up.
     * @return The index, -1 if none
     */
    private int findIndex(String id)
    {
    	
    	int size = passengerList.size();
    	for (int i = 0; i < size; i++)
    	{
    		Passenger p = passengerList.get(i);
    		if (p.getRefCode().equals(id))
    		{
    			return i;
    		}
    	}
    	return -1;
    }

    /**
     * @return The number of entries currently in the
     *         address book.
     */
    public int getNumberOfEntries()
    {
        return passengerList.size();
    }

 
    /**
     * @return All the staff details
     */
    public String listDetails()
    {
    	StringBuffer allEntries = new StringBuffer();
        for(Passenger details : passengerList) {
            allEntries.append(details);
            allEntries.append('\n');
        }
        return allEntries.toString();
    }
    
    /**
     * @return All the staff details in name order
     */
    public String listByName()
    {
    	// Collections.sort(passengerList, new StaffNameComparator());
    	return listDetails();
    }
    
    /**
     * @return All the staff details in id order
     */
    public String listByID()
    {
    	Collections.sort(passengerList);
    	return listDetails();
    }
}
