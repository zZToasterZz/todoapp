package com.example.dropwizard.consumer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

import com.example.dropwizard.config.RabbitEndPoint;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class QueueConsumer extends RabbitEndPoint implements Runnable, Consumer
{
	public QueueConsumer(String endPointName) throws IOException, TimeoutException
	{
		super(endPointName);
	}
	
	public void run()
	{
		try
		{
			channel.basicConsume(endPointName, true,this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Called when consumer is registered.
	 */
	public void handleConsumeOk(String consumerTag)
	{
		System.out.println("Consumer "+consumerTag +" registered");
	}

	/**
	 * Called when new message is available.
	 */
	@Override
	public void handleDelivery(String consumerTag, Envelope env, BasicProperties props, byte[] body) throws IOException
	{
		Map map = (HashMap)SerializationUtils.deserialize(body);
	    System.out.println("Message : "+ map.get("data"));
	}

	public void handleCancel(String consumerTag) {}
	public void handleCancelOk(String consumerTag) {}
	public void handleRecoverOk(String consumerTag) {}
	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}