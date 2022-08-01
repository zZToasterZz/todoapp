package com.todoapp.consumers;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todoapp.models.TasksRequest;
import com.todoapp.service.TasksService;

@Component
public class TasksConsumer
{
	@Autowired
	TasksService tasksService;
	
	@RabbitListener(queues = "${com.todoapp.queue.tasksqueue}")
	public void consumeTasksQueue(TasksRequest req)
	{
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
}