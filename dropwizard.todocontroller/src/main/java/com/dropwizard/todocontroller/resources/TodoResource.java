package com.dropwizard.todocontroller.resources;

import java.util.*;

import com.dropwizard.todocontroller.models.*;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todo")
public class TodoResource
{
	Client client;
	
	public TodoResource(Client client)
	{
		this.client=client;
	}
	
	@POST
	@Path("/add")
	public String addTask(TasksRequest request)
	{
		return "Accepted";
	}
	
	@PUT
	@Path("/update")
	public String upateTask(TasksRequest request)
	{
		return "Accepted";
	}
	
	@PATCH
	@Path("/complete/{id}")
	public String completeTask(Integer id)
	{
		return "Accepted";
	}
	
	@GET
	@Path("/fetch")
	@Produces(MediaType.APPLICATION_JSON)
	public List<?> getTasks()
	{
		WebTarget webTarget = client.target("http://localhost:9090/todo/fetch");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		@SuppressWarnings("rawtypes")
		ArrayList tasks = response.readEntity(ArrayList.class);
		
		return tasks;
	}
	
	@DELETE
	@Path("/delete/{id}")
	public String deleteTask(@PathParam("id") Integer id)
	{
		return "Accepted";
	}
}