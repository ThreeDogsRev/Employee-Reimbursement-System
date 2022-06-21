package com.revature.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This is the Hibernate Helper class
 * which is to handle startup and access Hibernate's
 * Session Factory to obtain a Session Object (connection to the DB).
 */
public class SessionHelper {
	
	private static Session session; // this is kind of like the Connection Interface from the JDBC API

	private static SessionFactory sf = new Configuration()
		.configure("hibernate.cfg.xml")
		.buildSessionFactory();
	
	
	/**
	 * We will use SessionFactory Interface to create a configuration object which 
	 * will call the .configure method to establish the connection to the DB based 
	 * on the creds we supplied in the hibernate.cfg.xml file.
	 */
	public static Session getSession() { // similar to getConnection()
		
		// call on our SessionFactory to open a connection if there isn't one
		if (session == null) {
			// open a session (a.k.a connection to the DB if there isn't one open already
			session = sf.openSession();
			
		}
		return session; // if the session is not null, just return it
	}
	
	public static void closeSession() {
		/**
		 * Ideally when we close a session it frees up that connection to the DB 
		 * and returns is to the connection pool so that is can be used by another
		 * thread or operation.
		 */
		session.close();
	}
	
}