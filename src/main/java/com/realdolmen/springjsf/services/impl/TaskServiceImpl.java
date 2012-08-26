package com.realdolmen.springjsf.services.impl;

import static com.realdolmen.springjsf.integration.TaskSpecs.descriptionLike;
import static com.realdolmen.springjsf.integration.TaskSpecs.dueDateBetween;
import static com.realdolmen.springjsf.integration.TaskSpecs.priorityEqual;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.integration.TaskRepository;
import com.realdolmen.springjsf.services.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskRepository taskRepository;

	@Override
	@Transactional
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public List<Task> findByDescriptionWithSpecification(String description) {
		return taskRepository.findAll(descriptionLike(description));
	}

	@Override
	public List<Task> findByDescriptionWithQuery(String description) {
		return taskRepository.findByDescription("%" + description + "%");
	}

	@Override
	public List<Task> findByDueDateBetween(Date begin, Date end) {
		return taskRepository.findAll(dueDateBetween(begin, end));
	}

	@Override
	public List<Task> findByExample(Task task) {
		Specifications<Task> specs = Specifications.where( //
				descriptionLike(task.getDescription()));
		if (task.getDueDate() != null) {
			specs.and(dueDateBetween(task.getDueDate(), task.getDueDate()));
		}
		if (task.getPriority() != null) {
			specs.and(priorityEqual(task.getPriority()));
		}
		return taskRepository.findAll(specs);
	}

	@Override
	public void remove(Task task) {
		taskRepository.delete(task.getId());
	}
}
