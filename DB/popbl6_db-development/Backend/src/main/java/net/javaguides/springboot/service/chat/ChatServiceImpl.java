package net.javaguides.springboot.service.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Chat;

import net.javaguides.springboot.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	public ChatServiceImpl(ChatRepository chatRepository) {
		super();
		this.chatRepository = chatRepository;
	}

	@Override
	public List<Chat> getAllChats() {
		return chatRepository.findAll();
	}

	@Override
	public void saveChat(Chat chat) {
		this.chatRepository.save(chat);

	}

	@Override
	public void deleteChatById(int id) {
		this.chatRepository.deleteById(id);

	}

	@Override
	public Chat getChatById(int id) {
		List<Chat> chats = getAllChats();
		for (Chat chat : chats) {
			if (chat.getId() == id) {
				return chat;
			}
		}
		//User user = userRepository.getOne(id);
		return null;

	}

}
