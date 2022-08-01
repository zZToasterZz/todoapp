package com.example.dropwizard;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;
import com.example.dropwizard.resources.TodoResource;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class DropwizardDemoApplication extends Application<DropwizardDemoConfiguration>
{
    public static void main(final String[] args) throws Exception
    {
        new DropwizardDemoApplication().run(args);
    }

    @Override
    public String getName()
    {
        return "DropwizardDemo";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardDemoConfiguration> bootstrap)
    {
    	
    }

    @Override
    public void run(final DropwizardDemoConfiguration configuration, final Environment environment)
    {
    	environment.healthChecks().register(getName(), new HealthCheck() {
			
			@Override
			protected Result check() throws Exception {
				
				return Result.healthy();
			}
		});
    	environment.jersey().register(new TodoResource());
    }
}