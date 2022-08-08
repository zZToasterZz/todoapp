package com.example.dropwizard.models;

import java.io.Serializable;
import java.util.*;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@EqualsAndHashCode
@ToString
@XmlRootElement
public class TodoRequest implements Serializable
{
	private Integer taskid;
	private String description;
	private Boolean complete;
	ArrayList<Integer> ar;
}