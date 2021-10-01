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

import net.javaguides.springboot.model.Chat;
import net.javaguides.springboot.service.chat.ChatService;

@RequestMapping("/chat")
@RestController
public class ChatController {

	ChatService chatService;
	BCryptPasswordEncoder passwordEncoder;

	public ChatController(ChatService chatService, BCryptPasswordEncoder passwordEncoder) {
		this.chatService = chatService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveChat(@RequestBody String payload, Model model) {
		Gson gson = new Gson();
		Chat chat = gson.fromJson(payload, Chat.class);
		chatService.saveChat(chat);

		return "Saved";
	}

	@GetMapping("/getAll")
	@ResponseStatus(HttpStatus.OK)
	public String getAllChats(Model model) {
		List<Chat> chats = chatService.getAllChats();
		Gson gson = new Gson();

		return gson.toJson(chats);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteChatByIdal(@PathVariable(value = "id") int id, Model model) {
		chatService.deleteChatById(id);
		return "Deleted";
	}

	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String getChatByIdal(@PathVariable(value = "id") int id, Model model) {
		Chat chat = chatService.getChatById(id);
		Gson gson = new Gson();

		return gson.toJson(chat);
	}
}
