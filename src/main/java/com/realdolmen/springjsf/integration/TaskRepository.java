package com.realdolmen.springjsf.integration;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.realdolmen.springjsf.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findByDescription(@Param("description") String description);

}
