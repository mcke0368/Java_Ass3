/* File FishStickClient.java
 * Author: Stanley Pieda
 * Created On: Jan 2018
 * Modified By: Jordan Mckenzie & Joel Schmuland
 * Modifed On: April 2018
 * Description: Client used to insert FishStick records on server.
 */
package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.UUID;

import org.hibernate.Session;

import datatransfer.FishStick;
import remoteinterface.FishStickService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.NotBoundException;


/**
 * The Class FishStickClient.
 * @author Jordan Mckenzie & Joel Schmuland
 */
public class FishStickClient {


	/** The buffered reader that reads in the user input. */
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	/** The FishStick object to be transferred to the server. */
	private FishStick fs = new FishStick();

	/**
	 * The main method.
	 * @author Joel Schmuland and Jordan Mckenzie
	 * @param command line arguments. In this iteration of the assignment, these are not used
	 */
	public static void main(String[] args) {
		new FishStickClient().runClient();
	}
	
	/**
	 * Constructor to instantiate the FishstickClient class
	 */
	public void FishstickClient(){	}

	/**
	 * Run client.
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	public void runClient() {
		
		// Set the port and server name to connect RMI service on
		int port = 1099;
		String serverName = new String("localhost");
		
		// Create the buffered reader for user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// Connect to the RMI service fro mthe server
		try {
			InetAddress myHost = Inet4Address.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Communication link failure");;
		}

		try {
			System.out.println("Attempting to connect to rmi://"+serverName+":"+port+"/FishStickService"); 
			FishStickService es = (FishStickService)Naming.lookup("rmi://"+serverName+":"+port+"/FishStickService"); // FishStickService is connected to as specified by the server

			do {
				try {
					// Build and insert the FishStick based on user input
					System.out.print("Enter data for new FishStick:\n");
					System.out.print("Please enter record number: ");
					fs.setRecordNumber(Integer.parseInt(br.readLine()));
					System.out.print("Please enter omega: ");
					fs.setOmega(br.readLine());
					System.out.print("Please enter lambda: ");
					fs.setLambda(br.readLine());
					String uuid = UUID.randomUUID().toString();
					fs.setUUID(uuid);
					es.insert(fs); // Insert the fishstick via the FishStickService
					FishStick fs = es.findByUUID(uuid); // Return FishStick info based on the UUID (PK) of the FishStick
					System.out.println("Command completed successfully. Returned FishStick: "+fs.toString());
					System.out.println("Do you want to insert another fish stick?(enter 'y' to insert again):"); // prompt to insert again

				}catch(IOException e){
					e.printStackTrace();
				}
			} while ( (br.readLine().equals("y")) ); // If user inputs anything other than 'y' break loop
			System.out.println("Client shutting down");
		}
		catch (MalformedURLException e) {
			System.out.println();
			System.out.println("MalformedURLException");
			System.out.println(e);
		}
		catch (RemoteException e) {
			System.out.println();
			System.out.println("RemoteException");
			System.out.println(e);
		}
		catch (NotBoundException e) {
			System.out.println();
			System.out.println("NotBoundException");
			System.out.println(e);
		}
		catch (Exception e) {
			System.out.println();
			System.out.println(e.getClass().getName());
			System.out.println(e);
		}
	}


	/**
	 * Generate UUID.
	 * @author Joel Schmuland and Jordan Mckenzie
	 *
	 * @return the uuid
	 */
	public UUID generateUUID() {
		return UUID.randomUUID();
	}


}
