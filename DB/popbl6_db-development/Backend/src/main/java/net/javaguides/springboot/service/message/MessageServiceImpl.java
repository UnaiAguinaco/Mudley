package net.javaguides.springboot.service.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Message;

import net.javaguides.springboot.repository.MessageRepository;


@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;



	public MessageServiceImpl(MessageRepository messageRepository) {
		super();
		this.messageRepository = messageRepository;
	}




	@Override
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}


	@Override
	public void saveMessage(Message chat) {
		this.messageRepository.save(chat);
		
	}


	@Override
	public void deleteMessageById(int id) {
		this.messageRepository.deleteById(id);
		
	}



	@Override
	public Message getMessageById(int id) {
		List<Message> messages = getAllMessages();
		for (Message message : messages) {
			if (message.getId() == id) {
				return message;
			}
		}
		//User user = userRepository.getOne(id);
		return null;
		
	}




}
