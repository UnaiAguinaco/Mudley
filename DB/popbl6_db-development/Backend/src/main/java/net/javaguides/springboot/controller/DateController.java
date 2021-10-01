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

import net.javaguides.springboot.model.Date;
import net.javaguides.springboot.service.date.DateService;

@RequestMapping("/date")
@RestController
public class DateController {

	DateService dateService;
	BCryptPasswordEncoder passwordEncoder;

	public DateController(DateService dateService, BCryptPasswordEncoder passwordEncoder) {
		this.dateService = dateService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveDate(@RequestBody String payload, Model model) {
		Gson gson = new Gson();
		Date date = gson.fromJson(payload, Date.class);
		dateService.saveDate(date);

		return "Saved";
	}

	@GetMapping("/getAll")
	@ResponseStatus(HttpStatus.OK)
	public String getAllDates(Model model) {
		List<Date> dates = dateService.getAllDates();
		Gson gson = new Gson();

		return gson.toJson(dates);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteDateByIdal(@PathVariable(value = "id") int id, Model model) {
		dateService.deleteDateById(id);
		return "Deleted";
	}

	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getDateByIdal(@PathVariable(value = "id") int id, Model model) {
		Date date = dateService.getDateById(id);
		Gson gson = new Gson();

		return gson.toJson(date);
	}
}
