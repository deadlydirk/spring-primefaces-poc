package com.realdolmen.springjsf.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Task extends AbstractEntity<Long, Long> {

	@NotEmpty
	@Size(max=48)
	private String description;

	@Min(0)
	@Max(10)
	private Integer priority;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	

}
