package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	/**
	 * Redirect to login page
	 * 
	 * @return String
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/politica_privacidad")
	public String politica_privacidad() {
		return "politica_privacidad";
	}

	/**
	 * Redirect the user depending the role
	 * 
	 * @param request
	 * @return String
	 */
	@GetMapping("/")
	public String returnToMain() {
		return "redirect:MainMenu";
	}

}
