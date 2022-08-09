package com.example.dropwizard.service;

import org.jvnet.hk2.annotations.Service;

import com.example.dropwizard.repository.TestRepo;

import jakarta.inject.Named;

@Service
@Named("test")
public class TestService implements TestRepo
{
	@Override
	public String getHello()
	{
		return "Hello from service!";
	}
}