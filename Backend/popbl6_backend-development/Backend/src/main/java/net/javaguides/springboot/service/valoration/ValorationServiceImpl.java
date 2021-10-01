package net.javaguides.springboot.service.valoration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import Conexiones.Conexion;
import net.javaguides.springboot.model.Chat;
import net.javaguides.springboot.model.Valoration;

import net.javaguides.springboot.repository.ValorationRepository;

@Service
public class ValorationServiceImpl implements ValorationService {

	@Autowired
	private ValorationRepository valorationRepository;

	public ValorationServiceImpl(ValorationRepository valorationRepository) {
		super();
		this.valorationRepository = valorationRepository;
	}

	@Override
	public List<Valoration> getAllValorations() {
		Conexion c= new Conexion();
	    String payload=c.guardarUsuario("getAllChats", null,"POST");
		Gson gson = new Gson();
		Valoration[] users = gson.fromJson(payload, Valoration[].class);
		List<Valoration> lista=new ArrayList<>();
		for (Valoration u: users) {
		   lista.add(u);
		}
		return lista;
	}

	@Override
	public void saveValoration(Valoration val) {
		this.valorationRepository.save(val);

	}

	@Override
	public void deleteValorationById(int id) {
		this.valorationRepository.deleteById(id);

	}

	@Override
	public Valoration getValorationById(int id) {
		return valorationRepository.getOne(id);

	}

}
