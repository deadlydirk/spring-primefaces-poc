package com.realdolmen.springjsf.services;

import java.util.List;

import com.realdolmen.springjsf.domain.Task;

public interface TaskService {

	public abstract Task save(Task task);

	public abstract List<Task> findAll();
	
	List<Task> findByDescription(String description);

}