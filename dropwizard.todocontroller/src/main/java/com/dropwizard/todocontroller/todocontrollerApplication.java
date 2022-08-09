package com.dropwizard.todocontroller;


import com.dropwizard.todocontroller.resources.TodoResource;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
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
    	final Client client = new JerseyClientBuilder(environment).using(config.getJerseyClientConfiguration())
                .build(getName());
    	environment.jersey().register(new TodoResource(client));
    }
}