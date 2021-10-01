package net.javaguides.springboot.service.gender;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import Conexiones.Conexion;
import net.javaguides.springboot.model.Gender;
import net.javaguides.springboot.model.Message;
import net.javaguides.springboot.repository.GenderRepository;


@Service
public class GenderServiceImpl implements GenderService {

	@Autowired
	private GenderRepository genderRepository;


	public GenderServiceImpl(GenderRepository genderRepository) {
		super();
		this.genderRepository = genderRepository;
	}


	@Override
	public List<Gender> getAllGenders() {
		Conexion c= new Conexion();
	    String payload=c.guardarUsuario("getAllGenders", null,"POST");
		Gson gson = new Gson();
		Gender[] users = gson.fromJson(payload, Gender[].class);
		List<Gender> lista=new ArrayList<>();
		for (Gender u: users) {
		   lista.add(u);
		}
		return lista;
	}


	@Override
	public void saveGender(Gender chat) {
		Conexion c= new Conexion();
	    String json = "{ \"id\": "+chat.getId()+", \"gender\": \""+chat.getName()+"\" }";		    
	    c.guardarUsuario("guardarGender", json,"POST");	
		
	}


	@Override
	public void deleteGenderById(int id) {
		Conexion c= new Conexion();
	    c.guardarUsuario("delete/"+id+"", null,"DELETE");
		
	}



	@Override
	public Gender getGenderById(int id) {
		Conexion c= new Conexion();
		String result;
		String json= "{ \"id\":"+id+" }";
	    result=c.guardarUsuario("getGenderId", json,"POST");
	    Gson gson = new Gson();
	    Gender message = gson.fromJson(result, Gender.class);
		return message;
		
	}


	@Override
	public Gender getGenderByName(String name) {
		List<Gender> lista = genderRepository.findAll();
		for(Gender g:lista) {
			if(g.getName().equals(name)) {
				return g;
			}
		}
		return getGenderById(1);
	}




}
