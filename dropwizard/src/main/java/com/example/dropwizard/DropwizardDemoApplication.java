package com.example.dropwizard;


import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;
import com.example.dropwizard.consumer.QueueConsumer;
import com.example.dropwizard.repository.TestRepo;
import com.example.dropwizard.resources.TodoResource;
import com.example.dropwizard.service.TestService;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.inject.Singleton;
import jakarta.ws.rs.client.Client;

public class DropwizardDemoApplication extends Application<DropwizardDemoConfiguration>
{
	public static final String BASE_URI = "http://localhost:8080/";
	
    public static void main(final String[] args) throws Exception
    {
        new DropwizardDemoApplication().run(args);
        
        QueueConsumer consumer = new QueueConsumer("dropwizardqueue");
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
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
    public void run(final DropwizardDemoConfiguration config, final Environment environment)
    {
    	final Client client = new JerseyClientBuilder(environment).using(config.getJerseyClientConfiguration())
                .build(getName());
    	environment.jersey().register(new TodoResource(client));
    	
    	environment.healthChecks().register(getName(), new HealthCheck() {
			@Override
			protected Result check() throws Exception {
				
				return Result.healthy();
			}
		});
    	
    	
    	environment.jersey().register(new AbstractBinder(){
			@Override
			protected void configure() {
				bind(TestService.class).to(TestRepo.class).in(Singleton.class);
			}
		});
    }
}