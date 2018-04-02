/* File FishStickClient.java
 * Author: Stanley Pieda
 * Created On: Jan 2018
 * Description: Client used to insert FishStick records on server.
 */
package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.UUID;

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

	/** The connection. */
	private Socket connection;

	/** The output. */
	private ObjectOutputStream output;

	/** The input. */
	private ObjectInputStream input;

	/** The message. */
	private String message = "";

	/** The server name. */
	private String serverName = "localhost";

	/** The port num. */
	private int portNum = 8081;

	/** The br. */
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	/** The fs. */
	// Added FishStick Object to be transfererred to server via Message object
	private FishStick fs = new FishStick();

	/**
	 * The main method.
	 * @author Joel Schmuland and Jordan Mckenzie
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		switch (args.length) {
		case 2:
			(new FishStickClient(args[1], Integer.parseInt(args[2]))).runClient();
			break;
		case 1:
			(new FishStickClient("localhost", Integer.parseInt(args[1]))).runClient();
			break;
		default:
			(new FishStickClient("localhost", 8081)).runClient();
		}

	}

	/**
	 * Instantiates a new fish stick client.
	 * @author Joel Schmuland and Jordan Mckenzie
	 *
	 * @param serverName the server name
	 * @param portNum the port num
	 */
	public FishStickClient(String serverName, int portNum) {
		this.serverName = serverName;
		this.portNum = portNum;
	}

	/**
	 * Run client.
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	public void runClient() {
		int port = 8082;
		String serverName = new String("localhost");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String myHostName = "localhost";
		
		try {
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
		} catch (UnknownHostException e) {
			System.out.println("Communication link failure");;
		}

		// Connect to the server
		try {
			connection = new Socket(InetAddress.getByName(serverName), portNum);
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			String message;
			System.out.println("Attempting to connect to rmi://"+serverName+":"+port+"/EchoService");
			FishStickService es = (FishStickService) // Changed to FishStickService
					Naming.lookup("rmi://"+serverName+":"+port+"/FishStickService");

			do {
				System.out.print("Input> ");
				try {
					System.out.print("Enter data for new FishStick:\n");
					System.out.print("Please enter record number: ");
					fs.setRecordNumber(Integer.parseInt(br.readLine()));
					System.out.print("Please enter omega: ");
					fs.setOmega(br.readLine());
					System.out.print("Please enter lambda: ");
					fs.setLambda(br.readLine());
					fs.setUUID(UUID.randomUUID().toString());
					//message = br.readLine();
						es.insert(fs);

				}catch(IOException e){
					System.out.println(e);
					message = null;
				}
			} while ( ! (br.readLine() == null || br.readLine().isEmpty()) );
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
