package com.dropwizard.todocontroller;


import java.util.EnumSet;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.dropwizard.todocontroller.resources.TodoResource;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.ws.rs.client.Client;

public class todocontrollerApplication extends Application<todocontrollerConfiguration>
{
    public static void main(final String[] args) throws Exception
    {
        new todocontrollerApplication().run(args);
    }

    @Override
    public String getName()
    {
        return "todocontroller";
    }

    @Override
    public void initialize(final Bootstrap<todocontrollerConfiguration> bootstrap)
    {
    }

    @Override
    public void run(final todocontrollerConfiguration config,final Environment environment)
    {
    	// Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD,PATCH");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    	
        //Configure Jersey client for external communication
    	final Client client = new JerseyClientBuilder(environment).using(config.getJerseyClientConfiguration())
                .build(getName());
    	environment.jersey().register(new TodoResource(client));
    }
}