package com.dropwizard.todocontroller.resources;

import java.util.*;

import com.dropwizard.todocontroller.models.*;
import com.dropwizard.todocontroller.producer.Producer;

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
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource
{
	Producer dropwizardqueue = new Producer("todoqueue");
	
	Client client;
	
	public TodoResource(Client client)
	{
		this.client=client;
	}
	
	@POST
	@Path("/add")
	public Response addTask(TasksRequest request)
	{
		request.setTaskid(0);
		request.setDelete("");
		request.setComplete(false);
		
		HashMap<String, TasksRequest> payLoad = new HashMap<>();
		payLoad.put("data", request);
		
		dropwizardqueue.sendMessage(payLoad);
		
		return Response.ok(new ResponseMessage("accepted")).build();
	}
	
	@PUT
	@Path("/update")
	public Response upateTask(TasksRequest request)
	{
		
		if(request.getTaskid() == null || request.getTaskid() == 0)
			return Response.status(400, "ID cannot be null or zero for updating a task.").build();
		
		request.setDelete("");
		request.setComplete(false);
		
		HashMap<String, TasksRequest> payLoad = new HashMap<>();
		payLoad.put("data", request);
		
		dropwizardqueue.sendMessage(payLoad);
		
		return Response.ok(new ResponseMessage("accepted")).build();
	}
	
	@PATCH
	@Path("/complete/{id}")
	public Response completeTask(@PathParam("id") Integer id)
	{
		if(id == 0)
			return Response.status(400, "ID cannot be null or zero for completing a task.").build();
		
		TasksRequest request = new TasksRequest();
		request.setTaskid(id);
		request.setComplete(true);
		
		HashMap<String, TasksRequest> payLoad = new HashMap<>();
		payLoad.put("data", request);
		
		dropwizardqueue.sendMessage(payLoad);
		
		return Response.ok(new ResponseMessage("accepted")).build();
	}
	
	@GET
	@Path("/fetch")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTasks()
	{
		WebTarget webTarget = client.target("http://localhost:9091/todo/fetch");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		@SuppressWarnings("rawtypes")
		ArrayList tasks = response.readEntity(ArrayList.class);
		
		return Response.ok(tasks).build();
	}
	
	@DELETE
	@Path("/delete/{id}")
	public Response deleteTask(@PathParam("id") Integer id)
	{
		TasksRequest request = new TasksRequest(id,"",false,"Y");
		
		HashMap<String, TasksRequest> payLoad = new HashMap<>();
		payLoad.put("data", request);
		
		dropwizardqueue.sendMessage(payLoad);
		
		return Response.ok(new ResponseMessage("accepted")).build();
	}
}