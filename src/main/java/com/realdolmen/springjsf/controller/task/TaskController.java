package com.realdolmen.springjsf.controller.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.realdolmen.springjsf.common.util.ReportUtils;
import com.realdolmen.springjsf.controller.AbstractCrudController;
import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.services.TaskService;

@Component("taskController")
@Scope("request")
public class TaskController extends AbstractCrudController<Task> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient TaskService taskService;

    private StreamedContent reportFile;

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

    public String saveAndReset() {
        String transition = save();
        setSubject(new Task());
        return transition;
    }

    @Override
    public String delete() {
        taskService.delete(taskService.findOne(Long.valueOf(getId())));
        return SUCCESS;
    }

    public void downloadTasksReport() throws JRException, IOException {
        String reportName = "taskReport";
        InputStream report = ReportUtils.createReport(reportName, createParameters(), getSubjects());
        reportFile = new DefaultStreamedContent(report, ReportUtils.PDF_CONTENT_TYPE, reportName + ReportUtils.PDF_EXTENSION);
    }

    private Map<String, Object> createParameters() {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(JRParameter.REPORT_LOCALE, getLocale());
        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE,
                ResourceBundle.getBundle(ReportUtils.REPORT_RESOURCE_BUNDLE, getLocale()));
        return parameters;

    }

    public StreamedContent getReportFile() throws JRException, IOException {
        downloadTasksReport();
        return reportFile;
    }
}
