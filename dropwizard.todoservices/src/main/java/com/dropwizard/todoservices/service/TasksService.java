package com.dropwizard.todoservices.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;

import com.dropwizard.todoservices.entity.Tasks;
import com.dropwizard.todoservices.models.TasksRequest;
import com.dropwizard.todoservices.models.TasksResponse;

import jakarta.persistence.Query;

public class TasksService
{
	Session session;
	
	public TasksService(Session session)
	{
		this.session=session;
	}
	
	public void addTask(TasksRequest request)
	{
		request.setComplete(false);
		
		session.beginTransaction();
		session.save(new Tasks(0, request.getDescription(), request.getComplete()));
		session.getTransaction().commit();
	}

	public void deleteTask(Integer id)
	{
		session.beginTransaction();
		Query query = session.createQuery("delete Tasks where taskid = :id");
		query.setParameter("id", id);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void completeTask(TasksRequest request)
	{
		Query query = session.createQuery("from Tasks where taskid = :id");
		query.setParameter("id", request.getTaskid());
		Tasks t = (Tasks)query.getResultList().get(0);
		t.setComplete(true);
		
		session.beginTransaction();
		session.update(t);
		session.getTransaction().commit();
		
	}

	public void updateTask(TasksRequest request)
	{
		Query query = session.createQuery("from Tasks where taskid = :id");
		query.setParameter("id", request.getTaskid());
		Tasks x = (Tasks)query.getResultList().get(0);
		
		Tasks t = new Tasks(request.getTaskid(), request.getDescription(), x.getComplete());
		
		session.beginTransaction();
		session.update(t);
		session.getTransaction().commit();
	}
	
	public List<TasksResponse> getAllTasks()
	{
		Query query = session.createQuery("from Tasks");
		
		List<Tasks> x = query.getResultList();
		
		return x.stream().map(i -> {
			return new TasksResponse(i.getTaskid(), i.getDescription(), i.getComplete());
		}).sorted(Comparator.comparing(TasksResponse::getTaskid)).collect(Collectors.toList());
	}
}