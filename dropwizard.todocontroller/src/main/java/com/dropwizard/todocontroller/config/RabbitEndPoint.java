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
    
	public RabbitEndPoint(String endpointName)
	{
	    this.endPointName = endpointName;
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    try
	    {
			connection = factory.newConnection();
			channel = connection.createChannel();
		    channel.queueDeclare(endpointName, false, false, false, null);
		}
	    catch (IOException | TimeoutException e)
	    {
			e.printStackTrace();
		}
	    
	}
    
	public void close()
	{
	    try
	    {
			this.channel.close();
			this.connection.close();
		}
	    catch (IOException | TimeoutException e)
	    {
			e.printStackTrace();
		}
	    
	}
}