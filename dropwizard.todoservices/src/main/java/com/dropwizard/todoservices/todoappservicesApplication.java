package com.dropwizard.todoservices;

import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dropwizard.todoservices.consumer.QueueConsumer;
import com.dropwizard.todoservices.consumer.TasksQueueHandler;
import com.dropwizard.todoservices.resources.TasksResource;
import com.dropwizard.todoservices.service.TasksService;
import com.dropwizard.todoservices.utils.HibernateUtil;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;

public class todoappservicesApplication extends Application<todoappservicesConfiguration>
{

    public static void main(final String[] args) throws Exception
    {
        new todoappservicesApplication().run(args);
        
        //Get Singleton instance of Hibernate configuration for injecting
    	HibernateUtil util = HibernateUtil.getInstance();
    	
        //Create service and inject hibernate session object
        TasksService tasksService = new TasksService(util.getSession());
        TasksQueueHandler handler = new TasksQueueHandler(tasksService);
        
        //start consumer thread. Switch to scheduled executor service later
        QueueConsumer consumer = new QueueConsumer("todoqueue", handler);
		/*Thread consumerThread = new Thread(consumer);
		consumerThread.start();*/
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		 scheduler.scheduleAtFixedRate(consumer, 1, 1, TimeUnit.SECONDS);
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
    	// Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD,PATCH");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        
        environment.jersey().register(new TasksResource());
    }
}