package com.realdolmen.springjsf.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JasperReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.services.TaskService;

@Scope("session")
@Component("taskController")
public class TaskController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskController.class);

	private Task task = new Task();
	private List<Task> tasks;

	@Autowired
	private TaskService taskService;

	public Task getTask() {
		return task;
	}

	public void saveTask() {
		LOGGER.debug("saving task: " + task);
		taskService.save(task);
		task = new Task();
		invalidateTasks();
	}

	private void invalidateTasks() {
		tasks = null;
	}

	public List<Task> getTasks() {
		if (tasks == null) {
			tasks = taskService.findAll();
		}
		return tasks;
	}
	
	public void downloadTasksReport(){
		Object context = FacesContext.getCurrentInstance().getExternalContext().getContext();
		LOGGER.debug(context.getClass().getName());
//		JasperReportsUtils.renderAsPdf(createReport(), createParameters(), createReportData(), null);
	}

	private List<Task> createReportData() {
		// TODO Auto-generated method stub
		return null;
	}

	private Map createParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	private JasperReport createReport() {
		// TODO Auto-generated method stub
		return null;
	}

}
