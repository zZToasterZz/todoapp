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
	public void consumeTasksQueue(TasksRequest req) throws Exception
	{
		System.out.println(req.toString());
		
		if(req.getTaskid() == 0)
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
				tasksService.updateTask(req); //update is id is present and delete is not Y
			}
		}
	}
}