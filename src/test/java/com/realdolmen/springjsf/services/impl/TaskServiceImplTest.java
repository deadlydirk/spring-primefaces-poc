package com.realdolmen.springjsf.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.services.TaskService;

@ContextConfiguration(locations = { "/spring/data-config.xml",
		"/spring/service-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TaskServiceImplTest {

	@Autowired
	private TaskService taskService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TaskServiceImplTest.class);

	@Test
	public void shouldSaveTaskAndFindIt() {
		Task oldTask = new Task();
		oldTask.setDescription("Sample Description");
		Task newTask = taskService.save(oldTask);
		Assert.assertNotNull("Saved task ID is null,probably not saved",
				newTask.getId());
		List<Task> tasks = taskService.findAll();
		LOGGER.warn(tasks.toString());
		assertFalse(tasks.isEmpty());
	}
	
	@Test
	public void shouldFindTasksByDescription() {
		List<Task> results = taskService.findByDescription("Task 1");
		assertEquals(1, results.size());
		
		results = taskService.findByDescription("task");
		assertEquals(4, results.size());
	}
}
