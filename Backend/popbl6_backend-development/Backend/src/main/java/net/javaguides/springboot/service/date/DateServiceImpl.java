package net.javaguides.springboot.service.date;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import Conexiones.Conexion;
import net.javaguides.springboot.model.Date;
import net.javaguides.springboot.model.Message;
import net.javaguides.springboot.repository.DateRepository;

@Service
public class DateServiceImpl implements DateService {

	@Autowired
	private DateRepository dateRepository;

	public DateServiceImpl(DateRepository dateRepository) {
		super();
		this.dateRepository = dateRepository;
	}

	@Override
	public List<Date> getAllDates() {
		Conexion c= new Conexion();
	    String payload=c.guardarUsuario("getAllDates", null,"POST");
		Gson gson = new Gson();
		Date[] users = gson.fromJson(payload, Date[].class);
		List<Date> lista=new ArrayList<>();
		for (Date u: users) {
		   lista.add(u);
		}
		return lista;
	}

	@Override
	public void saveDate(Date chat) {
		Conexion c= new Conexion();
		Gson gson = new Gson();
		String json = gson.toJson(chat);
	    c.guardarUsuario("guardarDate", json,"POST");

	}

	@Override
	public void deleteDateById(int id) {
		Conexion c= new Conexion();
	    c.guardarUsuario("delete/"+id+"", null,"DELETE");

	}

	@Override
	public Date getDateById(int id) {
		Conexion c= new Conexion();
		String result;
		String json= "{ \"id\":"+id+" }";
	    result=c.guardarUsuario("getDateId", json,"POST");
	    Gson gson = new Gson();
	    Date message = gson.fromJson(result, Date.class);
		return message;

	}

}
