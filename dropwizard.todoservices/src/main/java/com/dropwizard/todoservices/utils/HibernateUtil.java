package com.dropwizard.todoservices.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	public static HibernateUtil util;
	
	Session session;
	
	private HibernateUtil()
	{
		Configuration configuration = new Configuration();
    	configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        this.session=session;
	}
	
	public static HibernateUtil getInstance()
	{
		if(util == null)
		{
			util = new HibernateUtil();
			return util;
		}
		else
		{
			return util;
		}
	}
	
	public Session getSession()
	{
		return session;
	}
}