/* File FishStickServer.java
 * Author: Todd Kelley
 * Modified By: Stanley Pieda
 * Modifed On: Jan 2018
 * Description: RMI Server startup.
 */
package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
/*
 * Referenced works on shutting down RMI within the application:
 * https://coderanch.com/t/210114/java/Shut-RMI-Registry
 * https://community.oracle.com/thread/1180058?start=0&tstart=0
 */

public class FishStickServer {
	public static void main(String[] args) {
		try {
			int portNum = 1099;
			if(args.length > 0){
				portNum = Integer.parseInt(args[0]);
			}
			
			// ToDo: Create the remote service
			FishStickServiceImpl fishStickServiceImpl = null; // replace null
			// ToDo: create RMI registry, saving reference to it
			// message to users (this is done already on, next line)
			System.out.println( "Registry created" );
			
			// ToDo: export the remote service
			// ToDo: rebind using portNum with service name /FishStickService
			// message to users (this is done already on next line)
			System.out.println( "Exported" );
			
			
			System.out.println("Press any key to shutdown remote object and end program");
			Scanner input = new Scanner(System.in);
			input.nextLine(); // pause, let server-side admin close down connections
			
			fishStickServiceImpl.shutDownDao(); // close down Hibernate resources
			
			System.out.println("un-exporting remote object");
			UnicastRemoteObject.unexportObject(fishStickServiceImpl, true); // remove remote object
			
			//next lines not needed in this case, port 1099 is free on JVM exit according to TCPView
			//see: https://docs.microsoft.com/en-us/sysinternals/downloads/tcpview
			//System.out.println("Shutting down registry"); 
			//UnicastRemoteObject.unexportObject(registry, true);
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
			e.printStackTrace();
		}
	}
}
