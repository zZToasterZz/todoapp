package com.todoapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.entity.Tasks;
import com.todoapp.exceptions.BusinessException;
import com.todoapp.models.TasksRequest;
import com.todoapp.models.TasksResponse;
import com.todoapp.repository.TasksRepository;

@Service
public class TasksService
{
	@Autowired
	TasksRepository taskRepo;
	
	public List<TasksRequest> addAllTasks(List<TasksRequest> request)
	{
		List<Tasks> x = request.stream().map(i -> {
				return new Tasks(0, i.getDescription(), i.getComplete());
			}).collect(Collectors.toList());
		
		taskRepo.saveAll(x);
		
		return request;
	}
	
	public void addTask(TasksRequest request)
	{
		taskRepo.save(new Tasks(0, request.getDescription(), request.getComplete()));
	}
	
	public void updateTask(TasksRequest request)
	{
		if(taskRepo.findById(request.getTaskid()).isPresent())
		{
			taskRepo.save(new Tasks(request.getTaskid(), request.getDescription(), request.getComplete()));
		}
		else
		{
			//throw new BusinessException("No task present with id : "+request.getTaskid());
			
			//Log that no task is present and return, throwing exception will send the
			// consumer in an infinite error loop since the message is still present in the Queue
			return;
		}
	}
	
	public void completeTask(TasksRequest request)
	{
		Optional<Tasks> t = taskRepo.findById(request.getTaskid());
		if(t.isPresent())
		{
			taskRepo.save(new Tasks(t.get().getTaskid(), t.get().getDescription(), true));
		}
		else
		{
			//throw new BusinessException("No task present with id : "+request.getTaskid());
			
			//Log that no task is present and return, throwing exception will send the
			// consumer in an infinite error loop since the message is still present in the Queue
			return;
		}
	}
	
	public void deleteTask(Integer id)
	{
		if(taskRepo.findById(id).isPresent())
		{
			taskRepo.deleteById(id);
		}
	}
	
	public List<TasksResponse> getAllTasks()
	{
		List<Tasks> x = taskRepo.findAll();
		
		return x.stream().map(i -> {
			return new TasksResponse(i.getTaskid(), i.getDescription(), i.getComplete());
		}).collect(Collectors.toList());
	}
}