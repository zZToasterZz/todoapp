package com.todoapp.exceptions;

public class BusinessException extends RuntimeException
{
	private BusinessException(){}
	
	public BusinessException(String message)
	{
		super(message);
	}
}