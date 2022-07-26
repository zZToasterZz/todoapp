package com.example.dropwizard.producer;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

import com.example.dropwizard.config.RabbitEndPoint;

public class Producer extends RabbitEndPoint
{
	public Producer(String endpointName) throws IOException, TimeoutException
	{
		super(endpointName);
	}
	
	public void sendMessage(Serializable object) throws IOException
	{
	    channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
	}
}