package com.revature.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;

/**
 * This is the Hibernate Helper class
 * which is to handle startup and access Hibernate's
 * Session Factory to obtain a Session Object (connection to the DB).
 */
public class SessionHelper {
	private static SessionFactory sessionFactory = null;
	
	private SessionHelper() {

	}

	static {
		try {
			loadSessionFactory();
		} catch (Exception e) {
			System.err.println("Exception while initializing hibernate util.. ");
			e.printStackTrace();
		}
	}

	public static void loadSessionFactory() {

		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(Employee.class);
		configuration.addAnnotatedClass(Reimbursement.class);
		ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(srvcReg);
	}

	public static Session getSession() throws HibernateException {

		Session retSession = null;
		try {
			retSession = sessionFactory.openSession();
		} catch (Exception t) {
			System.err.println("Exception while getting session.. ");
			t.printStackTrace();
		}
		if (retSession == null) {
			System.err.println("session is discovered null");
		}

		return retSession;
	}
}