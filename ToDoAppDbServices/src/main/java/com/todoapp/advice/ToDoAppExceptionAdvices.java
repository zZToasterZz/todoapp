package com.todoapp.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todoapp.exceptions.BusinessException;

@ControllerAdvice
public class ToDoAppExceptionAdvices
{
	@ExceptionHandler(value = BusinessException.class)
	public void exception(BusinessException exception)
	{
		System.out.println(exception.getMessage());
		//Send data to Error queue ??
	}
}