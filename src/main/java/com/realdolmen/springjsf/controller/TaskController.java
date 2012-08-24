package com.realdolmen.springjsf.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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

	private Task task = new Task();
	private List<Task> tasks;
	private StreamedContent reportFile;
	private Locale locale = FacesContext.getCurrentInstance()
			.getExternalContext().getRequestLocale();

	@Autowired
	private TaskService taskService;

	public Task getTask() {
		return task;
	}

	public void saveTask() {
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

	public void downloadTasksReport() throws JRException, IOException {
		LOGGER.debug("download report");
		String reportName = "taskReport";
		InputStream report = ReportUtils.createReport(
				reportName, createParameters(), getTasks());
		reportFile = new DefaultStreamedContent(report,
				ReportUtils.PDF_CONTENT_TYPE, reportName + ReportUtils.PDF_EXTENSION);
	}

	private Map<String, Object> createParameters() {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(JRParameter.REPORT_LOCALE, locale);
		parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE,
				ResourceBundle.getBundle("MessageResources"));
		return parameters;
	}

	public StreamedContent getReportFile() throws JRException, IOException {
		downloadTasksReport();
		return reportFile;
	}

	public Locale getLocale() {
		return locale;
	}

}
