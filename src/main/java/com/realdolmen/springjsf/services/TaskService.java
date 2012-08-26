package com.realdolmen.springjsf.services;

import java.util.Date;
import java.util.List;

import com.realdolmen.springjsf.domain.Task;

public interface TaskService {

	Task save(Task task);
	
	void delete(Task task);

	List<Task> findAll();
	
	List<Task> findByDescriptionWithQuery(String description);
	
	List<Task> findByDescriptionWithSpecification(String description);
	
	List<Task> findByDueDateBetween(Date begin, Date end);
	
	List<Task> findByExample(Task task);

}