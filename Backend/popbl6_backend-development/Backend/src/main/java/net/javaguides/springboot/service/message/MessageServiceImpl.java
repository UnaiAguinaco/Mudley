package net.javaguides.springboot.service.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import Conexiones.Conexion;
import net.javaguides.springboot.model.Message;
import net.javaguides.springboot.model.User;
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
		Conexion c= new Conexion();
	    String payload=c.guardarUsuario("getAllMessages", null,"POST");
		Gson gson = new Gson();
		Message[] users = gson.fromJson(payload, Message[].class);
		List<Message> lista=new ArrayList<>();
		for (Message u: users) {
		   lista.add(u);
		}
		return lista;
	}


	@Override
	public void saveMessage(Message chat) {
		Conexion c= new Conexion();
		Gson gson = new Gson();
		System.out.println(chat.getChatId());
		System.out.println("qwerty");
		String json = gson.toJson(chat);
	    c.guardarUsuario("guardarMensaje", json,"POST");	
	}


	@Override
	public void deleteMessageById(int id) {
		Conexion c= new Conexion();
	    c.guardarUsuario("delete/"+id+"", null,"DELETE");
		
	}



	@Override
	public Message getMessageById(int id) {
		Conexion c= new Conexion();
		String result;
		String json= "{ \"id\":"+id+" }";
	    result=c.guardarUsuario("getMessageId", json,"POST");
	    Gson gson = new Gson();
	    Message message = gson.fromJson(result, Message.class);
		return message;
		
	}




}
