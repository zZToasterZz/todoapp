package com.dropwizard.todoservices.consumer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

import com.dropwizard.todocontroller.models.TasksRequest;
import com.dropwizard.todoservices.config.RabbitEndPoint;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class QueueConsumer extends RabbitEndPoint implements Runnable, Consumer
{
	TasksQueueHandler handler;
	
	public QueueConsumer(String endPointName, TasksQueueHandler handler) throws IOException, TimeoutException
	{
		super(endPointName);
		this.handler=handler;
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

	public void handleConsumeOk(String consumerTag)
	{
		//System.out.println("Consumer "+consumerTag +" registered");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleDelivery(String consumerTag, Envelope env, BasicProperties props, byte[] body) throws IOException
	{
		handler.handleDelivery(body);
	}

	public void handleCancel(String consumerTag) {}
	public void handleCancelOk(String consumerTag) {}
	public void handleRecoverOk(String consumerTag) {}
	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}