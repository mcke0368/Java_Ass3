/* File FishStickClient.java
 * Author: Stanley Pieda
 * Created On: Jan 2018
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
 * Ensure that all classes have Javadoc comments with your name
 * @author abc xyz
 */
public class FishStickClient {


	/** The br. */
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	/** The fs. */
	// Added FishStick Object to be transfererred to server
	private FishStick fs = new FishStick();

	/**
	 * The main method.
	 * @author Joel Schmuland and Jordan Mckenzie
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		new FishStickClient().runClient();
	}
	
	public void FishstickClient(){	}

	/**
	 * Run client.
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	public void runClient() {


		int port = 1099;
		String serverName = new String("localhost");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String myHostName = "localhost";

		try {
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
		} catch (UnknownHostException e) {
			System.out.println("Communication link failure");;
		}

		try {
			System.out.println("Attempting to connect to rmi://"+serverName+":"+port+"/EchoService");
			FishStickService es = (FishStickService) // Changed to FishStickService
					Naming.lookup("rmi://"+serverName+":"+port+"/FishStickService");

			do {
				System.out.println("Input> ");
				try {
					
					System.out.print("Enter data for new FishStick:\n");
					System.out.print("Please enter record number: ");
					fs.setRecordNumber(Integer.parseInt(br.readLine()));
					System.out.print("Please enter omega: ");
					fs.setOmega(br.readLine());
					System.out.print("Please enter lambda: ");
					fs.setLambda(br.readLine());
					String uuid = UUID.randomUUID().toString();
					fs.setUUID(uuid);
					es.insert(fs);
					FishStick fs = es.findByUUID(uuid);
					
					System.out.println("Command completed successfully. Returned FishStick: "+fs.toString());
					System.out.println("Do you want to insert another fish stick?(enter 'y' to insert again):"); // Ask to insert again

				}catch(IOException e){
					e.printStackTrace();
				}
			} while ( (br.readLine().equals("y")) );
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
