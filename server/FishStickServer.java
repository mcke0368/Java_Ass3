/* File FishStickServer.java
 * Author: Todd Kelley
 * Modified By: Stanley Pieda, Jordan Mckenzie & Joel Schmuland
 * Modifed On: April 2018
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

/**
 * The Class FishStickServer.
 * @author Jordan Mckenzie & Joel Schmuland
 */
public class FishStickServer {
    
    /**
     * The main method.
     *@author Joel Schmuland and Jordan Mckenzie
     * @param args the arguments
     * 
     */
    public static void main(String[] args) {
        FishStickServiceImpl fs = null;
        int portNum = 1099;
        if(args.length > 0){
                portNum = Integer.parseInt(args[0]);
        }

        try{
            fs = new FishStickServiceImpl();
            Registry registry = LocateRegistry.createRegistry(portNum);
            System.out.println( "Registry created" );
            UnicastRemoteObject.exportObject(fs, 0);
            System.out.println( "Exported" );
            Naming.rebind("//localhost:" + portNum + "/FishStickService", fs);

            // pause main thread until server admin presses a key.
            System.out.println("Press any key to shutdown remote object and end program");
            new java.util.Scanner(System.in).nextLine(); // pause, let server-side admin close down connections
        }catch (Exception e){
            System.out.println("Trouble: " + e);
            e.printStackTrace();
        }finally{
            if(fs != null){
                fs.shutDownDao(); // close down Hibernate resources
            }
        }

        System.out.println("un-exporting remote object");
        try{
            UnicastRemoteObject.unexportObject(fs, true); // remove remote object
        }catch(Exception e){
            System.out.println("Trouble: " + e);
        }
    }
}
