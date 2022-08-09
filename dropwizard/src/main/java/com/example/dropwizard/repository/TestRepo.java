package com.example.dropwizard.repository;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface TestRepo
{
	public String getHello();
}