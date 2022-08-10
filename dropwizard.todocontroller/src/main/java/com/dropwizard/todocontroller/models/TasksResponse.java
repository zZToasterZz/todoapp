package com.dropwizard.todocontroller.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TasksResponse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer taskid;
	private String description;
	private Boolean complete;
}