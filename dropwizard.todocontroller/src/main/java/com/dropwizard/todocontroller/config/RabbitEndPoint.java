package com.dropwizard.todocontroller.config;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class RabbitEndPoint
{
	protected Channel channel;
    protected Connection connection;
    protected String endPointName;
    
	public RabbitEndPoint(String endpointName) throws IOException, TimeoutException
	{
	    this.endPointName = endpointName;
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    channel.queueDeclare(endpointName, false, false, false, null);
	}
    
	public void close() throws IOException, TimeoutException
	{
	    this.channel.close();
	    this.connection.close();
	}
}