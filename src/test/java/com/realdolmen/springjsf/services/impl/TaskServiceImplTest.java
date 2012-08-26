package com.realdolmen.springjsf.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.services.TaskService;

@ContextConfiguration(locations = { //
"/spring/data-config.xml", //
		"/spring/service-config.xml" //
})
@RunWith(SpringJUnit4ClassRunner.class)
// make sure that the transactions are rolled back after each test
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TaskServiceImplTest {

	@Resource
	private TaskService taskService;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TaskServiceImplTest.class);

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
	public void shouldRemoveAllTasks() {
		List<Task> tasks = taskService.findAll();
		assertFalse(tasks.isEmpty());
		for (Task task : tasks) {
			taskService.remove(task);
		}
		tasks = taskService.findAll();
		assertTrue(tasks.isEmpty());
	}

	@Test
	public void shouldFindTasksByDescription() {
		List<Task> results = taskService
				.findByDescriptionWithSpecification("Task 1");
		assertEquals(1, results.size());

		results = taskService.findByDescriptionWithSpecification("task");
		assertEquals(4, results.size());
	}
}
