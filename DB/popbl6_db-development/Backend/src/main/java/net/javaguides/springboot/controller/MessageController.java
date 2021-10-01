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

import net.javaguides.springboot.model.Message;
import net.javaguides.springboot.service.message.MessageService;

@RequestMapping("/message")
@RestController
public class MessageController {

	MessageService messageService;
	BCryptPasswordEncoder passwordEncoder;

	public MessageController(MessageService messageService, BCryptPasswordEncoder passwordEncoder) {
		this.messageService = messageService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveMessage(@RequestBody String payload, Model model) {
		Gson gson = new Gson();
		Message message = gson.fromJson(payload, Message.class);
		messageService.saveMessage(message);

		return "Save";
	}

	@GetMapping("/getAll")
	@ResponseStatus(HttpStatus.OK)
	public String getAllMessages(Model model) {
		List<Message> messages = messageService.getAllMessages();
		Gson gson = new Gson();

		return gson.toJson(messages);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteMessageByIdal(@PathVariable(value = "id") Integer id, Model model) {
		messageService.deleteMessageById(id);
		return "Deleted";
	}

	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getMessageByIdal(@PathVariable(value = "id") int id, Model model) {
		Message message = messageService.getMessageById(id);
		Gson gson = new Gson();

		return gson.toJson(message);
	}
}
