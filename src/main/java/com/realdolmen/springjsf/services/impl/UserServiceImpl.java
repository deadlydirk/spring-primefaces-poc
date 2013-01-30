package com.realdolmen.springjsf.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.springjsf.domain.User;
import com.realdolmen.springjsf.integration.UserRepository;
import com.realdolmen.springjsf.services.UserService;
@Service("studentService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public User save(User student) {
		return userRepository.save(student);
	}

	@Transactional
	@Override
	public void delete(User user) {
		userRepository.delete(user.getId());
	}

	@Transactional(readOnly = true)
	@Override
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
