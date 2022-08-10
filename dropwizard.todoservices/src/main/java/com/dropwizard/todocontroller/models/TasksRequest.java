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
public class TasksRequest implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	Integer taskid=0;
	String description="";
	Boolean complete=false;
	String delete="N";
}