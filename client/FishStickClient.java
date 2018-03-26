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
import java.net.*;
import java.rmi.NotBoundException;

/**
 * Ensure that all classes have Javadoc comments with your name
 * @author abc xyz
 */
public class FishStickClient {

	public static void main(String[] args) {
		int port = 8082;
		String serverName = new String("localhost");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String myHostName = "localhost";

		switch (args.length) {
		case 0:
			break;
		case 1: 
			serverName = args[0];
			break;
		case 2:
			serverName = args[0];
			port = Integer.parseInt(args[1]);
			break;
		default:
			System.out.println("usage: EchoClient [hostname [portnum]]");
			break;
		}
		try {
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		try {
			String message;
			System.out.println("Attempting to connect to rmi://"+serverName+":"+port+"/EchoService");
			FishStickService es = (FishStickService) // Changed to FishStickService
					Naming.lookup("rmi://"+serverName+":"+port+"/EchoService");

			do {
				System.out.print("Input> ");
				try {
					message = br.readLine();
					if (message != null){
						System.out.println(es.processMessage(myHostName, message));
					}
				}catch(IOException e){
					System.out.println(e);
					message = null;
				}
			} while ( ! (message == null || message.isEmpty()) );
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
}
