package com.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.exceptions.BusinessException;
import com.todoapp.model.ResponseMessage;
import com.todoapp.model.TasksRequest;
import com.todoapp.model.TasksResponse;
import com.todoapp.service.TasksService;

@RestController
@RequestMapping("/todo")
public class ToDoController
{
	@Autowired
	TasksService taskService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addTask(@RequestBody TasksRequest request)
	{
		request.setDelete("");
		request.setComplete(false);
		
		taskService.queueTask(request);
		return ResponseEntity.ok(new ResponseMessage("Accepted"));
	}
	
	@PutMapping("/upate")
	public ResponseEntity<?> upateTask(@RequestBody TasksRequest request)
	{
		if(request.getTaskid() == 0)
			throw new BusinessException("ID cannot be empty");
		
		request.setDelete("");
		taskService.queueTask(request);
		return ResponseEntity.ok(new ResponseMessage("Accepted"));
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<TasksResponse[]> getTasks()
	{
		return ResponseEntity.ok(taskService.getTasks());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable Integer id)
	{
		taskService.queueTask(new TasksRequest(id,"",false,"Y") );
		return ResponseEntity.ok(new ResponseMessage("Accepted"));
	}
}