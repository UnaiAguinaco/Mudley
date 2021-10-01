package net.javaguides.springboot.service.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Chat;

@Service
public interface ChatService {
	List<Chat> getAllChats();

	void saveChat(Chat chat);

	void deleteChatById(int id);

	Chat getChatById(int id);

}
