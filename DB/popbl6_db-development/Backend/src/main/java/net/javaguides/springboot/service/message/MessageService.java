package net.javaguides.springboot.service.message;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Message;
@Service
public interface MessageService{
	List<Message> getAllMessages();

	void saveMessage(Message date);

	void deleteMessageById(int id);

	Message getMessageById(int id);

}
