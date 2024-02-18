//maintains a list of Flight objects as an ArrayList

import java.util.ArrayList;
import java.util.Collections;

public class FlightList
{
    // Storage for an arbitrary number of details.
    private ArrayList <Flight> FlightList;

    /**
     * Perform any initialization for the address book.
     */
    public FlightList()
    {
    	FlightList = new ArrayList<Flight>() ;
    }
    
    /**
     * Look up an id and return the
     * corresponding staff details.
     * @param idThe id  to be looked up.
     * @return The details corresponding to the id, null if none
     */
    public Flight findByCode(String flightCode)
    {
    	for (Flight f : FlightList)
    	{
    		if (f.getFlightCode().equals(flightCode))
    		{
    			return f;
    		}
    	}
    	return null;
    }
    


    /**
     * Add a new set of details to the list
     * @param details The details of the staff
     */
    public void addDetails(Flight details) 
    {
		FlightList.add(details);
    }
    
    /**
     * remove Staff object identified by this ID
     * @param id the ID identifying the person to be removed
     */
    public void removeDetails(String id) {
    	int index = findIndex(id);
        if (index != -1) {
        	FlightList.remove(index);
        }
    }
    /**
     * Look up an id and return index
     * @param id The id  to be looked up.
     * @return The index, -1 if none
     */
    private int findIndex(String flightCode)
    {
    	
    	int size = FlightList.size();
    	for (int i = 0; i < size; i++)
    	{
    		Flight f = FlightList.get(i);
    		if (f.getFlightCode().equals(flightCode))
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
        return FlightList.size();
    }

 
    /**
     * @return All the staff details
     */
    public String listDetails()
    {
    	StringBuffer allEntries = new StringBuffer();
        for(Flight details : FlightList) {
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
    	// Collections.sort(FlightList, new StaffNameComparator());
    	return listDetails();
    }
}
