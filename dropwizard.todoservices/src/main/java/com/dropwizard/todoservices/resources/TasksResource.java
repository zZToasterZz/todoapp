package com.dropwizard.todoservices.resources;

import java.util.List;

import com.dropwizard.todoservices.models.TasksResponse;
import com.dropwizard.todoservices.service.TasksService;

import jakarta.ws.rs.Path;

@Path("/todo")
public class TasksResource
{
	TasksService tasksService;
	
	public TasksResource(TasksService tasksService2)
	{
		this.tasksService = tasksService;
	}

	@Path("/fetch")
	public List<TasksResponse> fetchAll()
	{
		return tasksService.getAllTasks();
	}
}