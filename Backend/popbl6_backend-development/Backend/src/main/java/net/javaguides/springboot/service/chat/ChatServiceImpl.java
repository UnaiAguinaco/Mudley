package net.javaguides.springboot.service.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import Conexiones.Conexion;
import net.javaguides.springboot.model.Chat;
import net.javaguides.springboot.model.Message;
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
		Conexion c= new Conexion();
	    String payload=c.guardarUsuario("getAllChats", null,"POST");
		Gson gson = new Gson();
		Chat[] users = gson.fromJson(payload, Chat[].class);
		List<Chat> lista=new ArrayList<>();
		for (Chat u: users) {
		   lista.add(u);
		}
		return lista;
	}

	@Override
	public void saveChat(Chat chat) {
		Conexion c= new Conexion();
		Gson gson = new Gson();
		String json = gson.toJson(chat);
	    c.guardarUsuario("guardarChats", json,"POST");
	}

	@Override
	public void deleteChatById(int id) {
		this.chatRepository.deleteById(id);

	}

	@Override
	public Chat getChatById(int id) {
		Conexion c= new Conexion();
		String result;
		String json= "{ \"id\":"+id+" }";
	    result=c.guardarUsuario("getChatId", json,"POST");
	    Gson gson = new Gson();
	    Chat message = gson.fromJson(result, Chat.class);
		return message;

	}

}
