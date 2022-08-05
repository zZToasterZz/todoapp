package com.example.dropwizard.resources;

import java.util.ArrayList;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
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
	Client client;
	
	public TodoResource(Client client) {
		this.client=client;
	}

	@GET
	public String get()
	{
		WebTarget webTarget = client.target("https://jsonplaceholder.typicode.com/posts");
	    Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
	    Response response = invocationBuilder.get();
	    @SuppressWarnings("rawtypes")
	    ArrayList employees = response.readEntity(ArrayList.class);
	    return employees.toString();
	}
}