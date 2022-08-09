package com.dropwizard.todoservices.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

import com.dropwizard.todoservices.models.TasksRequest;
import com.dropwizard.todoservices.service.TasksService;

public class TasksQueueHandler
{
	private TasksService tasksService;
	
	public TasksQueueHandler(TasksService tasksService)
	{
		this.tasksService=tasksService;
	}
	
	public void handleDelivery(byte[] body)
	{
		Map<String, TasksRequest> map = (HashMap<String, TasksRequest>)SerializationUtils.deserialize(body);
	    TasksRequest req = (TasksRequest)map.get("data");
	    System.out.println(req.toString());
	    
	    if(req.getTaskid() == null || req.getTaskid() == 0)
		{
			tasksService.addTask(req); //add if id is 0
		}
		else
		{
			if(req.getDelete().equals("Y"))
			{
				tasksService.deleteTask(req.getTaskid()); //delete if id present and delete is Y
			}
			else
			{
				try
				{
					if(req.getComplete())
						tasksService.completeTask(req); //update if id is present and delete is not Y and complete is Y
					else
						tasksService.updateTask(req);
				}
				catch(NullPointerException e)
				{
					req.setComplete(false);
					tasksService.updateTask(req);
				}
			}
		}
	    
	}
	
	TasksQueueHandler(){}
}