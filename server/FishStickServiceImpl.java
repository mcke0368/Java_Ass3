/* File FishStickServiceImpl.java
 * Author: Stanley Pieda
 * Modified By: Jordan Mckenzie & Joel Schmuland
 * Modifed On: April 2018
 * Description: Remote Object that permits clients to insert
 *              FishStick records into a database, as well as
 *              retrieve them using String based uuid.
 */
package server;

import java.rmi.RemoteException;

import datatransfer.FishStick;

import dataaccesslayer.FishStickDao;
import dataaccesslayer.FishStickDaoImpl;

import remoteinterface.FishStickService;

/**
 * The Class FishStickServiceImpl.
 * @author Jordan Mckenzie & Joel Schmuland
 */
public class FishStickServiceImpl implements FishStickService  {
	
	
	/**
	 * Instantiates a new fish stick service impl.
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	public FishStickServiceImpl() {}
	
	/**
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	@Override
	public void insert(FishStick fishStick) throws RemoteException {
		// code to use the FishStickDaoImpl to insert a record
		FishStickDaoImpl.INSTANCE.insertFishStick(fishStick);
		
	}

	/**
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	@Override
	public FishStick findByUUID(String uuid) throws RemoteException {
		// code to use the FishStickDaoImpl to lookup and return a FishStick
		return FishStickDaoImpl.INSTANCE.findByUUID(uuid);
	}
	
	/**
	 * The shutDownDao method calls the shutdown method of the FishstickDaoImpl
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	public void shutDownDao() {
		FishStickDaoImpl.INSTANCE.shutdown();
	}
}
