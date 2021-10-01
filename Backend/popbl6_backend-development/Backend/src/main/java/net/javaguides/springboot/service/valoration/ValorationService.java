package net.javaguides.springboot.service.valoration;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Valoration;

@Service
public interface ValorationService {
	List<Valoration> getAllValorations();

	void saveValoration(Valoration valoration);

	void deleteValorationById(int id);

	Valoration getValorationById(int id);

}
