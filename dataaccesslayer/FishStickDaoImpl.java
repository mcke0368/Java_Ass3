/* File: FishStickDaoImpl.java
 * Author: Stanley Pieda
 * Date: Jan 2018
 * References:
 * Ram N. (2013). Data Access Object Design Pattern or DAO Pattern [blog] Retrieved from
 * http://ramj2ee.blogspot.in/2013/08/data-access-object-design-pattern-or.html
 */
package dataaccesslayer;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.jboss.logging.Message;

import datatransfer.FishStick;

/**
 * Ensure that all classes and class members have Javadoc comments with your name
 * @author abc xyz
 */
public enum FishStickDaoImpl implements FishStickDao{
	
	/** Only use one constant for Singleton Design Pattern */
	INSTANCE;

	private SessionFactory factory;
	private StandardServiceRegistry registry;
	
	private FishStickDaoImpl(){
		try {
			// A SessionFactory is set up once for an application!
			
			/**
			 * TODO: Review this code below
			 */
			MetadataImplementor meta = (MetadataImplementor) new MetadataSources(registry).addAnnotatedClass(FishStick.class).buildMetadata();
			factory = meta.buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, 
			// but if we are here we had trouble building the SessionFactory
			// so destroy it manually.
			if(registry != null) {
				shutdown();
			}
			throw e;
		}
	}

	@Override
	public void insertFishStick(FishStick fishStick){
		// code here to use Hibernate to insert a record.
		
		if(factory == null){
			return; // Should return a message if null
		}
		Datasource
		throw new RuntimeException("Method not impemented"); // remove this
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public FishStick findByUUID(String uuid){
		// Code here to use Hibernate to look up a record based on UUID
		throw new RuntimeException("Method not impemented"); // remove this
		
	}

	@Override
	public void shutdown() {
		// code here to close the factory, and to destroy the registry
		throw new RuntimeException("Method not impemented"); // remove this
	}
}
