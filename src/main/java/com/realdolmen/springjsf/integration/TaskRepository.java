package com.realdolmen.springjsf.integration;

import org.springframework.data.repository.CrudRepository;

import com.realdolmen.springjsf.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{

}
