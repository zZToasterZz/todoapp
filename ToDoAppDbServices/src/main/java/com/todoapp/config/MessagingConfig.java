package com.todoapp.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.todoapp.utils.RabbitMQUtils;

@Configuration
public class MessagingConfig
{
	@Autowired
	RabbitMQUtils mqUtil;
	
	@Bean
	public Queue tasksQueue()
	{
		return new Queue(mqUtil.getTasksqueue());
	}

	@Bean
	public MessageConverter converter()
	{
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate template(ConnectionFactory factory)
	{
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
}