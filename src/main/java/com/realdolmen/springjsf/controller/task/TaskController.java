package com.realdolmen.springjsf.controller.task;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.realdolmen.springjsf.controller.AbstractCrudController;
import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.services.TaskService;

@Component("taskController")
@Scope("request")
public class TaskController extends AbstractCrudController<Task> {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private transient TaskService taskService;

	public TaskController() {
		super(new Task());
	}
	
	@Override
	public List<Task> getSubjects() {
		return taskService.findAll();
	}

	@Override
	public String edit() {
		setId(getParameter(PARAMETER_ID));
		if (getId() != null) {
			setSubject(taskService.findOne(Long.valueOf(getId())));
		}
		return SUCCESS;
	}

	@Override
	public String save() {
		Task savedTask = getSubject();
		if (StringUtils.isNotBlank(getId())) {
			savedTask = taskService.findOne(Long.valueOf(getId()));
			savedTask.setDescription(getSubject().getDescription());
			savedTask.setDueDate(getSubject().getDueDate());
			savedTask.setPriority(getSubject().getPriority());
		}
		taskService.save(savedTask);
		return SUCCESS;
	}

	@Override
	public String delete() {
		taskService.delete(taskService.findOne(Long.valueOf(getId())));
		return SUCCESS;
	}
	
	
}
