
//program demonstrating using a list of Passenger objects
//adding and removing and listing and searching
//uses exceptions
import java.io.*;

public class ProgrammeDemo
{
    private PassengerList paxList;
    private FlightList fltList;
    // private PassengerListInterface interaction;
	// private PassengerListGUI gui;


    public ProgrammeDemo() 
    {
    	// Initialize empty list of passengers and flights
        paxList = new PassengerList();
        fltList = new FlightList();
        // This will handle text interaction with user
        // interaction = new PassengerListInterface(entries);
        
        BufferedReader buff = null;
    	String data [] = new String[6];
    	// Process PassengerList.txt
        try {
        	buff = new BufferedReader(new FileReader("PassengerList.txt"));
	    	String inputLine = buff.readLine();  //read first line
	    	while(inputLine != null){  
	    		//split line into parts
	    		data  = inputLine.split(";");
	    		//create Passenger object
	    		Passenger passenger = new Passenger(
	                    data[0],  // referenceCode
	                    data[1],  // firstName
	                    data[2],  // lastName
	                    data[3],  // flightCode
	                    Boolean.parseBoolean(data[4])
	                    );
	    		//add to list
	            paxList.addDetails(passenger);
	            //read next line
	            inputLine = buff.readLine();
	        }
        }
        catch(FileNotFoundException e) {
        	System.out.println(e.getMessage());
            System.exit(1);
        }
        catch (IOException e) {
        	e.printStackTrace();
            System.exit(1);        	
        }
        catch (NumberFormatException nfe) {
        	System.out.println(data[0] + ": Hours worked not a number :" + data[2]);
        	System.out.println("Program stopped");
        	System.exit(1);
        } finally {
            try { if (buff != null) buff.close(); } catch (IOException ioe) { /* don't do anything */ }
        }

        // Reset buffer reader for the next file
        buff = null;

        // Process FlightList.txt
        try {
            buff = new BufferedReader(new FileReader("FlightList.txt"));
            String inputLine = buff.readLine();  // Read first line
            while(inputLine != null) {
                data = inputLine.split(";");
                // Assuming the Flight text file has the same order as the class attributes
                Flight flight = new Flight(
                    data[0],  // flightCode
                    data[1],  // destination
                    data[2],  // carrier
                    Integer.parseInt(data[3]),  // maxPassengers
                    Double.parseDouble(data[4]),  // maxBaggageVolume
                    Double.parseDouble(data[5])  // maxBaggageWeight
                );
                fltList.addDetails(flight);
                inputLine = buff.readLine();  // Read next line
            }
        } catch(FileNotFoundException e) {
            System.out.println("FlightList.txt not found: " + e.getMessage());
            System.exit(1);
        } catch(IOException | NumberFormatException e) {
            System.out.println("Error processing FlightList.txt: " + e.getMessage());
            System.exit(1);
        } finally {
            try { if (buff != null) buff.close(); } catch (IOException ioe) { /* don't do anything */ }
        }
    }

    /**
     * Allow the user to interact with the Passenger list.
     */
    public void showInterface()
    {
        // interaction.run();
    }
    
    /**
     * Show GUI
    
    public void showGUI() {
        gui = new PassengerListGUI(entries);
        gui.setVisible(true);
    }     
     */
    
    
    public static void main (String arg[])  {
       	//creates demo object, with a populated Passenger list
    	ProgrammeDemo sld = new ProgrammeDemo();   
    	
    	//allow user to interact using a GUI
    	// sld.showGUI();

    	//allow user to interact with this list
    	//using text interface
    	sld.showInterface();	
    	
    }

}
