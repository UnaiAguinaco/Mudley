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

import net.javaguides.springboot.model.Valoration;
import net.javaguides.springboot.service.valoration.ValorationService;

@RequestMapping("/valoration")
@RestController
public class ValorationController {

	ValorationService valorationService;
	BCryptPasswordEncoder passwordEncoder;

	public ValorationController(ValorationService valorationService, BCryptPasswordEncoder passwordEncoder) {
		this.valorationService = valorationService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveValoration(@RequestBody String payload, Model model) {
		Gson gson = new Gson();
		Valoration valoration = gson.fromJson(payload, Valoration.class);
		valorationService.saveValoration(valoration);

		return "Saved";
	}

	@GetMapping("/getAll")
	@ResponseStatus(HttpStatus.OK)
	public String getAllMessages(Model model) {
		List<Valoration> valorations = valorationService.getAllValorations();
		Gson gson = new Gson();

		return gson.toJson(valorations);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteValorationByIdal(@PathVariable(value = "id") Integer id, Model model) {
		valorationService.deleteValorationById(id);
		return "Deleted";
	}

	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getValorationByIdal(@PathVariable(value = "id") int id, Model model) {
		Valoration valoration = valorationService.getValorationById(id);
		Gson gson = new Gson();

		return gson.toJson(valoration);
	}
}
