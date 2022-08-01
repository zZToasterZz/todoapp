package com.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.todoapp.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Integer>
{
}