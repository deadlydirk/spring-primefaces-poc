package com.realdolmen.springjsf.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.integration.TaskRepository;
import com.realdolmen.springjsf.services.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	@Transactional
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public List<Task> findAll() {
		return (List<Task>) taskRepository.findAll();
	}

}
