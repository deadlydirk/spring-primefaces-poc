package com.realdolmen.springjsf.integration.jpa;

import org.springframework.stereotype.Repository;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.integration.TaskRepository;

@Repository("taskRepository")
public class TaskRepositoryImpl extends GenericRepositoryImpl<Task, Long> implements TaskRepository {
	
}
