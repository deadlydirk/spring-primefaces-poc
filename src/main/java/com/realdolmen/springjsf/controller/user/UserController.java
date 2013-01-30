package com.realdolmen.springjsf.controller.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.realdolmen.springjsf.controller.AbstractCrudController;
import com.realdolmen.springjsf.domain.User;
import com.realdolmen.springjsf.services.UserService;

@Component("userController")
@Scope("request")
public class UserController extends AbstractCrudController<User> {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private transient UserService userService;

	public UserController() {
		super(new User());
	}
	
	@Override
	public List<User> getSubjects() {
		return userService.findAll();
	}

	@Override
	public String edit() {
		setId(getParameter(PARAMETER_ID));
		if (getId() != null) {
			setSubject(userService.findOne(Long.valueOf(getId())));
		}
		return SUCCESS;
	}

	@Override
	public String save() {
		User savedUser = getSubject();
		if (StringUtils.isNotBlank(getId())) {
			savedUser = userService.findOne(Long.valueOf(getId()));
			savedUser.setEmail(getSubject().getEmail());
			savedUser.setFirstName(getSubject().getFirstName());
			savedUser.setLastName(getSubject().getLastName());
			savedUser.setPassword(getSubject().getPassword());
			savedUser.setUsername(getSubject().getUsername());
		}
		userService.save(savedUser);
		
		return SUCCESS;
	}
	
	@Override
	public String delete() {
		userService.delete(userService.findOne(Long.valueOf(getId())));
		return SUCCESS;
	}
	
	
}
