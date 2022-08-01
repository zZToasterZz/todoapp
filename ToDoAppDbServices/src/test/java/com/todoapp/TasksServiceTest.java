package com.todoapp;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.todoapp.entity.Tasks;
import com.todoapp.models.TasksResponse;
import com.todoapp.repository.TasksRepository;
import com.todoapp.service.TasksService;

@SpringBootTest
public class TasksServiceTest
{
	@MockBean
	private TasksRepository taskRepo;
	
	@Autowired
	private TasksService taskService;
	
	@BeforeEach
	void setUp()
	{
		Tasks t = Tasks.builder().taskid(1).description("descr1").complete(false).build();
		
		when(taskRepo.findById(1)).thenReturn(Optional.of(t));
	}
	
	@Test
	public void addTaskTest()
	{
		List<TasksResponse> res = taskService.getAllTasks();
		
		System.out.println("START");
		for(TasksResponse e:res)
		{
			System.out.println(e.toString());
		}
	}
}