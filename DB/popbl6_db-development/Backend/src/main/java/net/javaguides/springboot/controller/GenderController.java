package net.javaguides.springboot.controller;

import java.util.List;

import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
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

import net.javaguides.springboot.model.Gender;
import net.javaguides.springboot.service.gender.GenderService;

@RequestMapping("/gender")
@RestController
public class GenderController {

	GenderService genderService;
	BCryptPasswordEncoder passwordEncoder;

	public GenderController(GenderService genderService, BCryptPasswordEncoder passwordEncoder) {
		this.genderService = genderService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveGender(@RequestBody String payload, Model model) {
		Gson gson = new Gson();
		Gender gender = gson.fromJson(payload, Gender.class);
		genderService.saveGender(gender);

		return "Saved";
	}

	@GetMapping("/getAll")
	@ResponseStatus(HttpStatus.OK)
	public String getAllGenders(Model model) {
		List<Gender> genders = genderService.getAllGenders();
		Gson gson = new Gson();

		return gson.toJson(genders);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteGenderByIdal(@PathVariable(value = "id") int id, Model model) {
		genderService.deleteGenderById(id);
		return "Deleted";
	}

	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getGenderByIdal(@PathVariable(value = "id") int id, Model model) {
		Gender gender = genderService.getGenderById(id);
		Gson gson = new Gson();

		return gson.toJson(gender);
	}
}
