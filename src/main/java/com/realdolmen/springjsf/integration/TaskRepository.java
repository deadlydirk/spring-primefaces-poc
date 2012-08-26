package com.realdolmen.springjsf.integration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.realdolmen.springjsf.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task>  {

	List<Task> findByDescription(@Param("description") String description);

}
