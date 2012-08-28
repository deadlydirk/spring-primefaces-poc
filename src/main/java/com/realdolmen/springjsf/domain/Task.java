package com.realdolmen.springjsf.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@NamedQueries({ //
@NamedQuery(name = "Task.findByDescription", query = "select t from Task t where lower(t.description) like lower(:description)") //
})
@Entity
public class Task extends AbstractEntity<Long, Long> {

	@NotEmpty(message = "{error.not.empty.description}")
	@Size(max = 48)
	private String description;

	@Min(0)
	@Max(10)
	private Integer priority;

	@Temporal(TemporalType.DATE)
	private Date dueDate;

	public Task() {
	}

	public Task(Integer priority, String description) {
		this.priority = priority;
		this.description = description;
	}

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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(description).append(priority)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return new EqualsBuilder()
				.append(getDescription(), other.getDescription())
				.append(getPriority(), other.getPriority()).isEquals();
	}

}
