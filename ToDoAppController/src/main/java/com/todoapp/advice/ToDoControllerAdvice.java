package com.todoapp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todoapp.exceptions.BusinessException;

@ControllerAdvice
public class ToDoControllerAdvice
{
	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<?> exception(BusinessException exception)
	{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}
}