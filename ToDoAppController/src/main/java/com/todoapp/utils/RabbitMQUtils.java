package com.todoapp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Component
public class RabbitMQUtils
{
	@Value("${com.todoapp.queue.tasksqueue}")
	private final String tasksqueue;
}