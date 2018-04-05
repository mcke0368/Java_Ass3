/* File: FishStickDaoImpl.java
 * Author: Stanley Pieda
 * Date: Jan 2018
 * Modified By: Jordan Mckenzie & Joel Schmuland
 * Modifed On: April 2018
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
 * The Enum FishStickDaoImpl.
 * @author Jordan Mckenzie & Joel Schmuland
 */
public enum FishStickDaoImpl implements FishStickDao{

	/**  Only use one constant for Singleton Design Pattern. */
	INSTANCE;

	/** The SessionFactory for the application. */
	private SessionFactory factory;
	
	/** The registry for the application. */
	private StandardServiceRegistry registry;

	/**
	 * Private constructor that when called via the INSTANCE of the enum,  the 
	 * session and registry are set up for the application
	 * @author Jordan Mckenzie & Joel Schmuland
	 */
	private FishStickDaoImpl(){
		try {
			// A SessionFactory is set up once for an application!
			registry =  new StandardServiceRegistryBuilder()
					.configure() // configures settings from hibernate.cfg.xml
					.build();

			MetadataImplementor meta = 
					(MetadataImplementor) new MetadataSources( registry )
					.addAnnotatedClass(FishStick.class) // Identifies the class that is correctly annotated and will be used
					.buildMetadata();
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

	/**
	 * insertFishStick uses the Hibernate session to insert a FishStick object into the DB
	 * @author Jordan Mckenzie & Joel Schmuland
	 * @param fishstick - The fishstick object to be inserted into the DB via the Hibernate session
	 */
	@Override
	public void insertFishStick(FishStick fishStick){

		if(factory == null){
			System.out.println("Session not Set!"); //Message displayed if null
			return;
		}

		// Open the session and complete the transaction to the DB
		Session s = null;
		Transaction tx = null;
		try{
			s = factory.openSession(); // Open the session to the DB
			tx = s.beginTransaction(); // Begin the transaction for the DB
			s.save(fishStick); // Save the fishStick to the DB
			tx.commit(); // Commit the transaction
		} catch(Exception e){
			if (tx!=null) tx.rollback(); //If any errors occurred, rollback the transaction as to not effect the DB
			throw e;
		}finally{
			s.close();
		}

	}



	/**
	 * findByUUID Finds the data for the associated FishStick by the UUID
	 * @author Joel Schmuland and Jordan Mckenzie
	 * @return Fishstick - the FishStick object that was found via the UUID
	 * @param uuid - String of UUID that identifies the Fishstick object that is to be located in the Database
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FishStick findByUUID(String uuid){
		
		List<FishStick> fsList; // To Hold the Fishstick Object(s)
		
		// Open the Session to get the Fishstick Object from the DB
		Session s = null;
		try{
			s = factory.openSession(); // Open the session to the DB

			fsList = s.createQuery("from FishStick where uuid = :uuid").setParameter("uuid",uuid).list(); //Query to find the FishStick by UUID

		} catch(Exception e){
			throw e;
		}finally{
			s.close(); // Close the session when completed
		}
		return fsList.get(0); // Return the Fishstick that was located via the UUID
	}

	/**
	 * shutdown Closes the factory and destroys the registry after the application has completed
	 * @author Joel Schmuland and Jordan Mckenzie
	 */
	@Override
	public void shutdown() {
		try{
			if(factory != null && factory.isClosed() == false){ // Check if the factory is closed. If not, close it
				factory.close();
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		try{
			if(registry != null){ // Check if the registry is destroyed, if not destroy it
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
