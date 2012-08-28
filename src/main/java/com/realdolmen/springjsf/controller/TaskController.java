package com.realdolmen.springjsf.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.realdolmen.springjsf.common.util.ReportUtils;
import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.services.TaskService;

@Scope("session")
@Component("taskController")
public class TaskController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskController.class);

	private Task newTask = new Task();
	private Task selectedTask = new Task();
	private List<Task> tasks;
	private StreamedContent reportFile;
	private Locale locale;

	@PostConstruct
	public void init() {
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		LOGGER.debug("current locale: " + locale);
	}
	
	@Autowired
	private TaskService taskService;

	private String goBackViewId;

	public void saveTask(Task task) {
		taskService.save(task);
		invalidateTasks();
	}

	public void saveNewTask() {
		saveTask(newTask);
		newTask = new Task();
	}

	public void saveNewTaskAndReset() {
		saveTask(newTask);
		newTask = new Task();
	}

	public void saveSelectedTask() {
		saveTask(selectedTask);
	}

	public String deleteSelectedTask() {
		taskService.delete(selectedTask);
		invalidateTasks();
		return "taskList";
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

	public void downloadTasksReport() throws JRException, IOException {
		LOGGER.debug("download report, language: " + locale.getLanguage());
		String reportName = "taskReport";
		InputStream report = ReportUtils.createReport(reportName,
				createParameters(), getTasks());
		reportFile = new DefaultStreamedContent(report,
				ReportUtils.PDF_CONTENT_TYPE, reportName
						+ ReportUtils.PDF_EXTENSION);
	}

	private Map<String, Object> createParameters() {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(JRParameter.REPORT_LOCALE, locale);
		parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE,
				ResourceBundle.getBundle(ReportUtils.REPORT_RESOURCE_BUNDLE, locale));
		return parameters;
	}

	public StreamedContent getReportFile() throws JRException, IOException {
		downloadTasksReport();
		return reportFile;
	}

	public Locale getLocale() {
		return locale;
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public String createNewTask() {
		newTask = new Task();
		return "taskCreate";
	}

	private void setGoBackViewId() {
		this.goBackViewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
	}

	public String goBack() {
		return goBackViewId;
	}

	public Task getNewTask() {
		return newTask;
	}

	public void setNewTask(Task newTask) {
		this.newTask = newTask;
	}

	public String editTask() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		Long currentTaskId = Long.valueOf(params.get("currentTaskId"));
		selectedTask = taskService.findOne(currentTaskId);
		LOGGER.debug("selected task with id = " + currentTaskId.toString());
		setGoBackViewId();
		return "taskEdit";
	}

}
