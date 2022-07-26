package com.example.dropwizard.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.example.dropwizard.models.TodoRequest;
import com.example.dropwizard.producer.Producer;
import com.example.dropwizard.repository.TestRepo;
import com.example.dropwizard.service.TestService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;

@Path("/todo")
public class TodoResource
{
	@Inject
	@Named("test")
	TestRepo repo;
	
	Client client;
	
	public TodoResource(Client client)
	{
		this.client=client;
	}

	@POST
	@Path("/add")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public TodoRequest get(TodoRequest request) throws IOException, TimeoutException
	{
		System.out.println(request.toString());
		
		ArrayList<Integer> ar = new ArrayList<>();
		ar.add(10);
		ar.add(20);
		ar.add(30);
		request.setAr(ar);
		
		HashMap<String, TodoRequest> payLoad = new HashMap<>();
		payLoad.put("data", request);
		
		Producer producer = new Producer("dropwizardqueue");
		producer.sendMessage(payLoad);
		
	    return request;
	}
	
	@GET
	@Path("/test")
	public String hello()
	{
		return repo.getHello();
	}
}