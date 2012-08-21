package com.realdolmen.springjsf.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.integration.jpa.TaskRepositoryImpl;
import com.realdolmen.springjsf.services.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepositoryImpl taskDao;

	@Override
	@Transactional
	public Task save(Task task) {
		return taskDao.persist(task);
	}

	@Override
	public List<Task> findAll() {
		return taskDao.findAll();
	}

}
