package com.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.todoapp.repository.TasksRepository;
import com.todoapp.service.TasksService;

@SpringBootTest
public class TasksServiceTest
{
	@MockBean
	private TasksRepository taskRepo;
	
	@Autowired
	private TasksService taskService;
	
	
	public void addTaskTest()
	{
		
	}
}