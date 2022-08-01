package com.todoapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todoapp.exceptions.BusinessException;
import com.todoapp.model.TasksRequest;
import com.todoapp.model.TasksResponse;
import com.todoapp.utils.RabbitMQUtils;

@Service
public class TasksService
{
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	RabbitMQUtils mqUtils;
	
	public TasksResponse[] getTasks()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		
		TasksResponse[] response=null;
		ResponseEntity<TasksResponse[]> res = restTemplate.exchange("http://localhost:9091/todo/fetch", HttpMethod.GET, entity, TasksResponse[].class);
		
		if(res.getStatusCode() == HttpStatus.OK)
		{
			response = res.getBody();
		}
		else
		{
			throw new BusinessException("No tasks present");
		}
		
		return response;
	}
	
	public void addTask(TasksRequest request)
	{
		request.setTaskid(0);
		request.setDelete("");
		request.setComplete(false);
		
		rabbitTemplate.convertAndSend(mqUtils.getTasksqueue(),request);
	}
	
	public void updateTask(TasksRequest request)
	{
		if(request.getTaskid() == null || request.getTaskid() == 0)
			throw new BusinessException("ID cannot be empty");
		
		request.setDelete("");
		request.setComplete(false);
		
		rabbitTemplate.convertAndSend(mqUtils.getTasksqueue(),request);
	}
	
	public void completeTask(Integer id)
	{
		if(id == 0)
			throw new BusinessException("ID cannot be 0");
		
		TasksRequest request = new TasksRequest();
		request.setTaskid(id);
		request.setComplete(true);
		
		rabbitTemplate.convertAndSend(mqUtils.getTasksqueue(),request);
	}
	
	public void deleteTask(TasksRequest request)
	{
		rabbitTemplate.convertAndSend(mqUtils.getTasksqueue(),request);
	}
	
	public void addToQueue(TasksRequest request)
	{
		rabbitTemplate.convertAndSend(mqUtils.getTasksqueue(),request);
	}
}