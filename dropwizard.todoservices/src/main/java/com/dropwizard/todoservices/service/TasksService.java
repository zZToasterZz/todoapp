package com.dropwizard.todoservices.service;

import org.hibernate.Session;

import com.dropwizard.todoservices.entity.Tasks;
import com.dropwizard.todoservices.models.TasksRequest;

public class TasksService
{
	Session session;
	
	public TasksService(Session session)
	{
		this.session=session;
	}
	
	public void addTask(TasksRequest request)
	{
		request.setComplete(false);
		session.save(new Tasks(0, request.getDescription(), request.getComplete()));
	}
}