package com.realdolmen.springjsf.integration;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.realdolmen.springjsf.domain.Task;
import com.realdolmen.springjsf.domain.Task_;

public final class TaskSpecs {

	private TaskSpecs() {
	}
	
	public static Specification<Task> descriptionLike(final String description) {
		return new Specification<Task>() {
			@Override
			public Predicate toPredicate(Root<Task> taskRoot,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(taskRoot.get(Task_.description)), "%"
						+ description.toLowerCase() + "%");
			}
		};
	}
	
	public static Specification<Task> dueDateBetween(final Date begin, final Date end) {
		return new Specification<Task>() {
			@Override
			public Predicate toPredicate(Root<Task> taskRoot,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.between(taskRoot.get(Task_.dueDate), begin, end);
			}
		};
	}
	
	public static Specification<Task> priorityEqual(final Integer priority) {
		return new Specification<Task>() {
			@Override
			public Predicate toPredicate(Root<Task> taskRoot,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(taskRoot.get(Task_.priority), priority);
			}
		};
	}
	
}
