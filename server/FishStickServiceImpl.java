/* File FishStickServiceImpl.java
 * Author: Stanley Pieda
 * Modified By: 
 * Modifed On: Jan 2018
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
 * Ensure that all classes have Javadoc comments with your name
 * @author abc xyz
 */
public class FishStickServiceImpl implements FishStickService  {
	
	
	public FishStickServiceImpl() {}
	
	@Override
	public void insert(FishStick fishStick) throws RemoteException {
		// code to use the FishStickDaoImpl to insert a record
		FishStickDaoImpl.INSTANCE.insertFishStick(fishStick);
		
	}

	@Override
	public FishStick findByUUID(String uuid) throws RemoteException {
		// code to use the FishStickDaoImpl to lookup and return a FishStick
		return FishStickDaoImpl.INSTANCE.findByUUID(uuid);
	}
	
	// Do not place this method into remote interface. i.e. no @Override
	public void shutDownDao() {
		FishStickDaoImpl.INSTANCE.shutdown();
	}
}
