package com.dropwizard.todoservices.resources;

import java.util.List;

import com.dropwizard.todoservices.models.TasksResponse;
import com.dropwizard.todoservices.service.TasksService;
import com.dropwizard.todoservices.utils.HibernateUtil;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todo")
public class TasksResource
{
	TasksService tasksService;
	
	public TasksResource()
	{
		if(tasksService == null)
		{
			HibernateUtil util = HibernateUtil.getInstance();
			tasksService = new TasksService(util.getSession());
		}
	}
	
	public TasksResource(TasksService tasksService2)
	{
		this.tasksService = tasksService;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/fetch")
	public Response /*List<TasksResponse>*/ fetchAll()
	{
		return Response.ok(tasksService.getAllTasks()).build();
		//return tasksService.getAllTasks();
	}
}