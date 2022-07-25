package com.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todoapp.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Integer>
{
}