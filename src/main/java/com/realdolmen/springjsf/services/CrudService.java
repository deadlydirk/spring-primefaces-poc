package com.realdolmen.springjsf.services;

import java.util.List;

public interface CrudService<T> {

	T save(T t);

	void delete(T t);

	T findOne(Long id);

	List<T> findAll();

}
