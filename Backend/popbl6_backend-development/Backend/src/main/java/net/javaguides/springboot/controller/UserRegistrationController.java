package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.controller.dto.UserRegistrationDto;
import net.javaguides.springboot.model.Gender;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.gender.GenderService;
import net.javaguides.springboot.service.user.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;
	private GenderService genderService;

	public UserRegistrationController(UserService userService, GenderService genderService) {
		super();
		this.userService = userService;
		this.genderService = genderService;
	}

	/**
	 * Create a UserRegistrationDto object to create a new user
	 * 
	 * @return UserRegistrationDto
	 */
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@ModelAttribute("generos")
	public List<Gender> genderGetter() {

		return genderService.getAllGenders();
	}

	/**
	 * Redirect to the registration page
	 * 
	 * @return String
	 */
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}

	/**
	 * Save an user in the database and redirect to the corresponding login page
	 * 
	 * @param registrationDto
	 * @return String
	 */
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		boolean exist = false;
		for (User user : userService.getAllUsers()) {
			if (user.getEmail().equals(registrationDto.getEmail())) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			if (registrationDto.getGenderId().equals("0")) {
				registrationDto.setGenderId("1");
			}
			userService.save(registrationDto);
			return "redirect:/login";
		} else {
			return "redirect:/login?error";
		}
	}
}
