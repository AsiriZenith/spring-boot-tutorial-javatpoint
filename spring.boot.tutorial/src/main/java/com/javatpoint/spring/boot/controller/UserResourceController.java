package com.javatpoint.spring.boot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javatpoint.spring.boot.exception.UserNotFoundException;
import com.javatpoint.spring.boot.model.User;
import com.javatpoint.spring.boot.service.UserDaoService;

@RestController
public class UserResourceController {

	@Autowired
	private UserDaoService userDaoService;
	
	@GetMapping("/users")
	public List<User> retriveAllUsers()  
	{  
		return userDaoService.findAll();  
	} 
	
	@GetMapping("/users/{id}")  
	public User retriveUser(@PathVariable int id)  
	{  
		User user = userDaoService.findOne(id);  
		if (user == null) {
			throw new UserNotFoundException("id: "+id);
		}
		return user;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = userDaoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();  
	}
}
