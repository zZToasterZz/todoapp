package com.todoapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.models.TasksRequest;
import com.todoapp.models.TasksResponse;
import com.todoapp.service.TasksService;

@RestController
@RequestMapping("/todo")
public class TasksController
{
	@Autowired
	TasksService tasksService;
	
	@GetMapping("/fetch")
	public List<TasksResponse> getTodoList()
	{
		return tasksService.getAllTasks();
	}
}