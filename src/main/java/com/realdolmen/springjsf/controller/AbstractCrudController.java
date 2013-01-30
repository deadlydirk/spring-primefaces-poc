package com.realdolmen.springjsf.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.realdolmen.springjsf.domain.AbstractEntity;

public abstract class AbstractCrudController<Subject extends AbstractEntity<?,?>> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	public static final String SUCCESS = "success";
	public static final String PARAMETER_ID = "id";
	private Subject subject;
	private Locale locale;
	
	@PostConstruct
	public void init() {
		setLocale(FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}
	
	public AbstractCrudController(Subject subject) {
		this.subject = subject;
	}
	
	public abstract List<Subject> getSubjects();
	public abstract String save();
	public abstract String edit();
	public abstract String delete();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getParameter(String name) {
        return getRequest().getParameter(name);
    }
	
	
	
	/**
     * Servlet API Convenience method
     *
     * @return HttpServletRequest from the FacesContext
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
}
