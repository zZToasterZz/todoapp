package com.dropwizard.todoservices;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dropwizard.todoservices.consumer.QueueConsumer;
import com.dropwizard.todoservices.consumer.TasksQueueHandler;
import com.dropwizard.todoservices.service.TasksService;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class todoappservicesApplication extends Application<todoappservicesConfiguration>
{

    public static void main(final String[] args) throws Exception
    {
        new todoappservicesApplication().run(args);
    }

    @Override
    public String getName()
    {
        return "todoappservices";
    }

    @Override
    public void initialize(final Bootstrap<todoappservicesConfiguration> bootstrap)
    {
    	
    }

    @Override
    public void run(final todoappservicesConfiguration config, final Environment environment) throws IOException, TimeoutException
    {
    	//Hibernate Config
    	Configuration configuration = new Configuration();
    	configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        
        //Register service and inject hibernate session object
        environment.jersey().register(new TasksService(session));
        
        //start consumer thread. Switch to executor service later
        QueueConsumer consumer = new QueueConsumer("tododropwizardqueue", TasksQueueHandler.getInstance());
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
    }
}