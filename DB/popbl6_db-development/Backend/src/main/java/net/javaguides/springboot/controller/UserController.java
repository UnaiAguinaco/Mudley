package net.javaguides.springboot.controller;

import java.util.List;

import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.user.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

	UserService userService;
	BCryptPasswordEncoder passwordEncoder;

	public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveUser(@RequestBody String payload, Model model) {
		Gson gson = new Gson();
		User user = gson.fromJson(payload, User.class);
		userService.saveUser(user);
	
		return "Saved";
	}

	@GetMapping("/getAll")
	@ResponseStatus(HttpStatus.OK)
	public String getAllUsers(Model model) {
		
		List<User> users = userService.getAllUsers();
		Gson gson = new Gson();

		return gson.toJson(users);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteUserByIdal(@PathVariable(value = "id") int id, Model model) {
		userService.deleteUserByIdal(id);
		return "Deleted";
	}

	@GetMapping("/load/{email}")
	@ResponseStatus(HttpStatus.OK)
	public String loadUserByUsername(@PathVariable(value = "email") String email, Model model) {
		UserDetails userDetails = userService.loadUserByUsername(email);
		Gson gson = new Gson();

		return gson.toJson(userDetails);
	}

	@GetMapping("/getId/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getUserByIdal(@PathVariable(value = "id") int id, Model model) {
		User user = userService.getUserByIdal(id);
		Gson gson = new Gson();

		return gson.toJson(user);
	}

	@GetMapping("/getEmail/{email}")
	@ResponseStatus(HttpStatus.OK)
	public String getUserByEmail(@PathVariable(value = "email")  String email, Model model) {
		User user = userService.getUserByEmail(email);
		Gson gson = new Gson();

		return gson.toJson(user);
	}
}
